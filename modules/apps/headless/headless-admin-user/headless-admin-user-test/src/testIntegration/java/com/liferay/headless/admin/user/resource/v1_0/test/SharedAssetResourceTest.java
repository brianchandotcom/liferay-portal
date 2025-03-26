/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.SharedAsset;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.interpreter.SharingEntryInterpreterProvider;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class SharedAssetResourceTest extends BaseSharedAssetResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_objectDefinition = _getObjectDefinition();

		_user = UserTestUtil.addUser();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_objectDefinitionLocalService.deleteObjectDefinition(_objectDefinition);

		_userLocalService.deleteUser(_user);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"actionIds", "assetType", "externalReferenceCode", "id", "title"
		};
	}

	@Override
	protected SharedAsset
			testGetMyUserAccountSharedAssetsSharedByMePage_addSharedAsset(
				SharedAsset sharedAsset)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId());

		_objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), testGroup.getGroupId(),
			_objectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"title", sharedAsset.getTitle()
			).build(),
			serviceContext);

		return _toSharedAsset(
			sharedAsset,
			_sharingEntryLocalService.addSharingEntry(
				null, TestPropsValues.getUserId(), 0, _user.getUserId(),
				_classNameLocalService.getClassNameId(
					_objectDefinition.getClassName()),
				_objectEntry.getObjectEntryId(), testGroup.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, serviceContext));
	}

	@Override
	protected SharedAsset
			testGetMyUserAccountSharedAssetsSharedWithMePage_addSharedAsset(
				SharedAsset sharedAsset)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), _user.getUserId());

		_objectEntry = _objectEntryLocalService.addObjectEntry(
			_user.getUserId(), testGroup.getGroupId(),
			_objectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"title", sharedAsset.getTitle()
			).build(),
			serviceContext);

		return _toSharedAsset(
			sharedAsset,
			_sharingEntryLocalService.addSharingEntry(
				null, _user.getUserId(), 0, TestPropsValues.getUserId(),
				_classNameLocalService.getClassNameId(
					_objectDefinition.getClassName()),
				_objectEntry.getObjectEntryId(), testGroup.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), null, serviceContext));
	}

	private static ObjectDefinition _getObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				true, ObjectDefinitionTestUtil.getRandomName(),
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).indexed(
						true
					).indexedAsKeyword(
						true
					).name(
						"title"
					).localized(
						false
					).build()),
				ObjectDefinitionConstants.SCOPE_SITE,
				TestPropsValues.getUserId());

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getObjectDefinitionId(), "title");

		objectDefinition.setTitleObjectFieldId(objectField.getObjectFieldId());

		return _objectDefinitionLocalService.updateObjectDefinition(
			objectDefinition);
	}

	private String _getAssetTypeTitle(SharingEntry sharingEntry)
		throws Exception {

		SharingEntryInterpreter sharingEntryInterpreter =
			_sharingEntryInterpreterProvider.getSharingEntryInterpreter(
				sharingEntry);

		if (sharingEntryInterpreter == null) {
			return null;
		}

		return sharingEntryInterpreter.getAssetTypeTitle(
			sharingEntry, LocaleUtil.US);
	}

	private SharedAsset _toSharedAsset(
			SharedAsset sharedAsset, SharingEntry sharingEntry)
		throws Exception {

		return new SharedAsset() {
			{
				actionIds = TransformUtil.transformToArray(
					SharingEntryAction.getSharingEntryActions(
						sharingEntry.getActionIds()),
					SharingEntryAction::getActionId, String.class);
				assetType = _getAssetTypeTitle(sharingEntry);
				dateCreated = sharingEntry.getCreateDate();
				dateModified = sharingEntry.getModifiedDate();
				externalReferenceCode = sharingEntry.getExternalReferenceCode();
				id = sharingEntry.getSharingEntryId();
				title = sharedAsset.getTitle();
			}
		};
	}

	private static ObjectDefinition _objectDefinition;

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private static ObjectFieldLocalService _objectFieldLocalService;

	private static User _user;

	@Inject
	private static UserLocalService _userLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private ObjectEntry _objectEntry;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private SharingEntryInterpreterProvider _sharingEntryInterpreterProvider;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

}