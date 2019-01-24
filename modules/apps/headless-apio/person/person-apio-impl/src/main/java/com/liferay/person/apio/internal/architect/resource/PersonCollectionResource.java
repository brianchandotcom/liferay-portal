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
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.comparator.UserLastNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import io.vavr.control.Try;

import java.io.ByteArrayOutputStream;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;
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
@Component(immediate = true, service = CollectionResource.class)
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
						personCreatorForm.getHonorificPrefix(), 0);
					long suffixId = _getSuffixId(
						personCreatorForm.getHonorificSuffix(), 0);

					User user = _userLocalService.addUser(
						UserConstants.USER_ID_DEFAULT,
						themeDisplay.getCompanyId(), true, null, null,
						personCreatorForm.needsAlternateName(),
						personCreatorForm.getAlternateName(),
						personCreatorForm.getEmail(), 0, StringPool.BLANK,
						LocaleUtil.getDefault(),
						personCreatorForm.getGivenName(), StringPool.BLANK,
						personCreatorForm.getFamilyName(), prefixId, suffixId,
						true, personCreatorForm.getBirthdayMonth(),
						personCreatorForm.getBirthdayDay(),
						personCreatorForm.getBirthdayYear(),
						personCreatorForm.getJobTitle(), null, null, null, null,
						false, new ServiceContext());

					byte[] bytes = _getImageBytes(
						personCreatorForm.getImageBinaryFile());

					_userLocalService.updatePortrait(user.getUserId(), bytes);

					return new UserWrapper(user, themeDisplay);
				});
		}
		catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	@SuppressWarnings("Convert2MethodRef")
	private byte[] _getImageBytes(BinaryFile binaryFile) {
		return Try.of(
			() -> binaryFile.getInputStream()
		).mapTry(
			inputStream -> {
				ByteArrayOutputStream byteArrayOutputStream =
					new ByteArrayOutputStream();

				StreamUtil.transfer(inputStream, byteArrayOutputStream);

				return byteArrayOutputStream.toByteArray();
			}
		).getOrNull();
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

	private long _getPrefixId(String honorificTitle, long defaultTitle) {
		if (honorificTitle == null) {
			return defaultTitle;
		}

		return Optional.ofNullable(
			_listTypeService.getListType(
				honorificTitle, ListTypeConstants.CONTACT_PREFIX)
		).map(
			ListTypeModel::getListTypeId
		).orElseThrow(
			() -> new BadRequestException(
				"Unable to find honorific title: " + honorificTitle)
		);
	}

	private long _getSuffixId(String honorificTitle, long defaultTitle) {
		if (honorificTitle == null) {
			return defaultTitle;
		}

		return Optional.ofNullable(
			_listTypeService.getListType(
				honorificTitle, ListTypeConstants.CONTACT_SUFFIX)
		).map(
			ListTypeModel::getListTypeId
		).orElseThrow(
			() -> new BadRequestException(
				"Unable to find honorific title: " + honorificTitle)
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

		String oldAlternateName = user.getScreenName();

		Contact contact = user.getContact();

		long prefixId = _getPrefixId(
			personUpdaterForm.getHonorificPrefix(), contact.getPrefixId());
		long suffixId = _getSuffixId(
			personUpdaterForm.getHonorificSuffix(), contact.getSuffixId());

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(user.getBirthday());

		int oldBirthdayMonth = calendar.get(Calendar.MONTH);
		int oldBirthdayDay = calendar.get(Calendar.DATE);
		int oldBirthdayYear = calendar.get(Calendar.YEAR);

		String oldJobTitle = user.getJobTitle();

		user = _userLocalService.updateUser(
			user.getUserId(), user.getPassword(),
			personUpdaterForm.getPassword(), personUpdaterForm.getPassword(),
			false, user.getReminderQueryQuestion(),
			user.getReminderQueryAnswer(),
			personUpdaterForm.getAlternateName(oldAlternateName),
			personUpdaterForm.getEmail(), user.getFacebookId(),
			user.getOpenId(), false, null, user.getLanguageId(),
			user.getTimeZoneId(), user.getGreeting(), user.getComments(),
			personUpdaterForm.getGivenName(), user.getMiddleName(),
			personUpdaterForm.getFamilyName(), prefixId, suffixId, true,
			personUpdaterForm.getBirthdayMonth(oldBirthdayMonth),
			personUpdaterForm.getBirthdayDay(oldBirthdayDay),
			personUpdaterForm.getBirthdayYear(oldBirthdayYear),
			contact.getSmsSn(), contact.getFacebookSn(), contact.getJabberSn(),
			contact.getSkypeSn(), contact.getTwitterSn(),
			personUpdaterForm.getJobTitle(oldJobTitle), user.getGroupIds(),
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