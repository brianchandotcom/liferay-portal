/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.service.impl;

import com.liferay.info.exception.InfoItemPermissionException;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.translation.constants.TranslationActionKeys;
import com.liferay.translation.constants.TranslationConstants;
import com.liferay.translation.exception.XLIFFFileException;
import com.liferay.translation.internal.util.XLIFFLocaleIdUtil;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.security.permission.TranslationPermission;
import com.liferay.translation.service.base.TranslationEntryServiceBaseImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the translation entry remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * <code>com.liferay.translation.service.TranslationEntryService</code>
 * interface. <p> This is a remote service. Methods of this service are expected
 * to have security checks based on the propagated JAAS credentials because this
 * service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    TranslationEntryServiceBaseImpl
 */
@Component(
	property = {
		"json.web.service.context.name=translation",
		"json.web.service.context.path=TranslationEntry"
	},
	service = AopService.class
)
public class TranslationEntryServiceImpl
	extends TranslationEntryServiceBaseImpl {

	@Override
	public TranslationEntry addOrUpdateTranslationEntry(
			long groupId, InfoItemReference infoItemReference, String content,
			String contentType, ServiceContext serviceContext)
		throws PortalException {

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			throw new NoSuchInfoItemException(
				"Unable to add or update a translation entry without a class " +
					"PK info item identifier");
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		try {
			String languageId = _language.getLanguageId(
				LocaleUtil.fromLanguageId(
					String.valueOf(
						XLIFFLocaleIdUtil.getTargetLocaleId(
							_saxReader.read(content)))));

			_checkPermission(groupId, languageId, infoItemReference);

			return translationEntryLocalService.addOrUpdateTranslationEntry(
				groupId, infoItemReference.getClassName(),
				classPKInfoItemIdentifier.getClassPK(), content, contentType,
				languageId, serviceContext);
		}
		catch (DocumentException documentException) {
			throw new XLIFFFileException.MustHaveCorrectEncoding(
				documentException);
		}
	}

	@Override
	public TranslationEntry addOrUpdateTranslationEntry(
			long groupId, String languageId,
			InfoItemReference infoItemReference,
			InfoItemFieldValues infoItemFieldValues,
			ServiceContext serviceContext)
		throws PortalException {

		_checkPermission(groupId, languageId, infoItemReference);

		return translationEntryLocalService.addOrUpdateTranslationEntry(
			groupId, languageId, infoItemReference, infoItemFieldValues,
			serviceContext);
	}

	@Override
	public TranslationEntry deleteTranslationEntry(long translationEntryId)
		throws PortalException {

		_modelResourcePermission.check(
			getPermissionChecker(), translationEntryId, ActionKeys.DELETE);

		return translationEntryLocalService.deleteTranslationEntry(
			translationEntryId);
	}

	private void _checkPermission(
			long groupId, String languageId,
			InfoItemReference infoItemReference)
		throws InfoItemPermissionException, PrincipalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		InfoItemPermissionProvider<JournalArticle> infoItemPermissionProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemPermissionProvider.class,
				infoItemReference.getClassName());

		if (!infoItemPermissionProvider.hasPermission(
				permissionChecker, infoItemReference, ActionKeys.UPDATE)) {

			String name = TranslationConstants.RESOURCE_NAME + "." + languageId;

			if (!_translationPermission.contains(
					permissionChecker, groupId, languageId,
					TranslationActionKeys.TRANSLATE)) {

				throw new PrincipalException.MustHavePermission(
					permissionChecker, name, name,
					TranslationActionKeys.TRANSLATE);
			}
		}
	}

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private Language _language;

	@Reference(
		target = "(model.class.name=com.liferay.translation.model.TranslationEntry)"
	)
	private ModelResourcePermission<TranslationEntry> _modelResourcePermission;

	@Reference
	private SAXReader _saxReader;

	@Reference
	private TranslationPermission _translationPermission;

}