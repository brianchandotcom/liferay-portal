/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.person.apio.internal.architect.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.organization.apio.architect.identifier.OrganizationIdentifier;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.person.apio.internal.architect.form.PersonCreatorForm;
import com.liferay.person.apio.internal.architect.form.PersonUpdaterForm;
import com.liferay.person.apio.internal.model.UserWrapper;
import com.liferay.person.apio.internal.query.FullNameQuery;
import com.liferay.person.apio.internal.util.UserAccountRepresentorBuilderHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.ListTypeModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ListTypeService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.comparator.UserLastNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/Person">Person </a> resources through a web API. The
 * resources are mapped from the internal model {@link UserWrapper}.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class PersonCollectionResource
	implements CollectionResource<UserWrapper, Long, PersonIdentifier> {

	@Override
	public CollectionRoutes<UserWrapper, Long> collectionRoutes(
		CollectionRoutes.Builder<UserWrapper, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, FullNameQuery.class, Credentials.class,
			ThemeDisplay.class
		).addCreator(
			this::_addUser, ThemeDisplay.class, _hasPermission::forAdding,
			PersonCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "user-account";
	}

	@Override
	public ItemRoutes<UserWrapper, Long> itemRoutes(
		ItemRoutes.Builder<UserWrapper, Long> builder) {

		return builder.addGetter(
			this::_getUserWrapper, ThemeDisplay.class
		).addRemover(
			idempotent(_userService::deleteUser), _hasPermission::forDeleting
		).addUpdater(
			this::_updateUser, ThemeDisplay.class, _hasPermission::forUpdating,
			PersonUpdaterForm::buildForm
		).build();
	}

	@Override
	public Representor<UserWrapper> representor(
		Representor.Builder<UserWrapper, Long> builder) {

		Representor.FirstStep<UserWrapper> userWrapperFirstStep =
			_userAccountRepresentorBuilderHelper.buildUserWrapperFirstStep(
				builder);

		userWrapperFirstStep.addRelatedCollection(
			"organizations", OrganizationIdentifier.class);
		userWrapperFirstStep.addRelatedCollection(
			"webSites", WebSiteIdentifier.class);

		return userWrapperFirstStep.build();
	}

	private UserWrapper _addUser(
		PersonCreatorForm personCreatorForm, ThemeDisplay themeDisplay) {

		try {
			return TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					long prefixId = _getPrefixId(
						personCreatorForm.getHonorificPrefix(),
						ListTypeConstants.CONTACT_PREFIX, 0);
					long suffixId = _getPrefixId(
						personCreatorForm.getHonorificSuffix(),
						ListTypeConstants.CONTACT_SUFFIX, 0);

					User user = _userLocalService.addUser(
						UserConstants.USER_ID_DEFAULT,
						themeDisplay.getCompanyId(), true, null, null,
						personCreatorForm.needsAlternateName(),
						personCreatorForm.getAlternateName(),
						personCreatorForm.getEmail(), 0, StringPool.BLANK,
						LocaleUtil.getDefault(),
						personCreatorForm.getGivenName(), StringPool.BLANK,
						personCreatorForm.getFamilyName(), prefixId, suffixId,
						personCreatorForm.isMale(),
						personCreatorForm.getBirthdayMonth(),
						personCreatorForm.getBirthdayDay(),
						personCreatorForm.getBirthdayYear(),
						personCreatorForm.getJobTitle(), null, null, null, null,
						false, new ServiceContext());

					byte[] bytes = _getImageBytes(personCreatorForm);

					_userLocalService.updatePortrait(user.getUserId(), bytes);

					return new UserWrapper(user, themeDisplay);
				});
		}
		catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	private String _getAlternateName(
		PersonUpdaterForm personUpdaterForm, User user) {

		if (personUpdaterForm.getAlternateName() == null) {
			return user.getScreenName();
		}

		return personUpdaterForm.getAlternateName();
	}

	private Integer _getDefaultValue(
		Optional<Integer> optional, int defaultValue) {

		return optional.orElse(defaultValue);
	}

	private byte[] _getImageBytes(PersonCreatorForm personCreatorForm) {
		return Try.fromFallible(
			personCreatorForm::getImageBinaryFile
		).map(
			BinaryFile::getInputStream
		).map(
			this::_readInputStream
		).orElse(
			null
		);
	}

	private PageItems<UserWrapper> _getPageItems(
			Pagination pagination, FullNameQuery fullNameQuery,
			Credentials credentials, ThemeDisplay themeDisplay)
		throws PortalException {

		PermissionChecker permissionChecker =
			(PermissionChecker)credentials.get();

		if (!permissionChecker.isCompanyAdmin(themeDisplay.getCompanyId())) {
			throw new PrincipalException.MustBeCompanyAdmin(permissionChecker);
		}

		Optional<String> optional = fullNameQuery.getFullNameOptional();

		if (optional.isPresent()) {
			List<User> users = _userLocalService.search(
				themeDisplay.getCompanyId(), optional.get(),
				WorkflowConstants.STATUS_APPROVED, null,
				pagination.getStartPosition(), pagination.getEndPosition(),
				new UserLastNameComparator());

			List<UserWrapper> userWrappers = _toUserWrappers(
				users, themeDisplay);

			int count = _userLocalService.searchCount(
				themeDisplay.getCompanyId(), optional.get(),
				WorkflowConstants.STATUS_APPROVED, null);

			return new PageItems<>(userWrappers, count);
		}

		List<User> users = _userLocalService.getUsers(
			themeDisplay.getCompanyId(), false,
			WorkflowConstants.STATUS_APPROVED, pagination.getStartPosition(),
			pagination.getEndPosition(), null);

		List<UserWrapper> userWrappers = _toUserWrappers(users, themeDisplay);

		int count = _userLocalService.getUsersCount(
			themeDisplay.getCompanyId(), false,
			WorkflowConstants.STATUS_APPROVED);

		return new PageItems<>(userWrappers, count);
	}

	private long _getPrefixId(
		String honorificTitle, String className, long defaultTitle) {

		return Try.fromFallible(
			() -> _listTypeService.getListType(honorificTitle, className)
		).map(
			ListTypeModel::getListTypeId
		).orElse(
			defaultTitle
		);
	}

	private UserWrapper _getUserWrapper(long userId, ThemeDisplay themeDisplay)
		throws PortalException {

		if (themeDisplay.getDefaultUserId() == userId) {
			throw new NotFoundException();
		}

		User user = _userService.getUserById(userId);

		return new UserWrapper(user, themeDisplay);
	}

	private Boolean _isMale(PersonUpdaterForm personUpdaterForm, User user)
		throws PortalException {

		Optional<Boolean> optional = personUpdaterForm.isMaleOptional();

		return optional.orElse(user.isMale());
	}

	private byte[] _readInputStream(InputStream inputStream)
		throws IOException {

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		byte[] bytes = new byte[1024];
		int value = -1;

		while ((value = inputStream.read(bytes)) != -1) {
			byteArrayOutputStream.write(bytes, 0, value);
		}

		byteArrayOutputStream.flush();

		return byteArrayOutputStream.toByteArray();
	}

	private List<UserWrapper> _toUserWrappers(
		List<User> users, ThemeDisplay themeDisplay) {

		return Stream.of(
			users
		).flatMap(
			List::stream
		).map(
			user -> new UserWrapper(user, themeDisplay)
		).collect(
			Collectors.toList()
		);
	}

	private UserWrapper _updateUser(
			long userId, PersonUpdaterForm personUpdaterForm,
			ThemeDisplay themeDisplay)
		throws PortalException {

		User user = _userService.getUserById(userId);

		Contact contact = user.getContact();

		long prefixId = _getPrefixId(
			personUpdaterForm.getHonorificPrefix(),
			ListTypeConstants.CONTACT_PREFIX, contact.getPrefixId());
		long suffixId = _getPrefixId(
			personUpdaterForm.getHonorificSuffix(),
			ListTypeConstants.CONTACT_SUFFIX, contact.getSuffixId());

		String alternateName = _getAlternateName(personUpdaterForm, user);

		Date birthdayDate = user.getBirthday();

		user = _userLocalService.updateUser(
			user.getUserId(), user.getPassword(),
			personUpdaterForm.getPassword(), personUpdaterForm.getPassword(),
			false, user.getReminderQueryQuestion(),
			user.getReminderQueryAnswer(), alternateName,
			personUpdaterForm.getEmail(), user.getFacebookId(),
			user.getOpenId(), false, null, user.getLanguageId(),
			user.getTimeZoneId(), user.getGreeting(), user.getComments(),
			personUpdaterForm.getGivenName(), user.getMiddleName(),
			personUpdaterForm.getFamilyName(), prefixId, suffixId,
			_isMale(personUpdaterForm, user),
			_getDefaultValue(
				personUpdaterForm.getBirthdayMonthOptional(),
				birthdayDate.getMonth()),
			_getDefaultValue(
				personUpdaterForm.getBirthdayDayOptional(),
				birthdayDate.getDate()),
			_getDefaultValue(
				personUpdaterForm.getBirthdayYearOptional(),
				birthdayDate.getYear()),
			contact.getSmsSn(), contact.getFacebookSn(), contact.getJabberSn(),
			contact.getSkypeSn(), contact.getTwitterSn(),
			personUpdaterForm.getJobTitle(), user.getGroupIds(),
			user.getOrganizationIds(), user.getRoleIds(), null,
			user.getUserGroupIds(), new ServiceContext());

		return new UserWrapper(user, themeDisplay);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.User)"
	)
	private HasPermission<Long> _hasPermission;

	@Reference
	private ListTypeService _listTypeService;

	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private UserAccountRepresentorBuilderHelper
		_userAccountRepresentorBuilderHelper;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

}