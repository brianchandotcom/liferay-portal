/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DDMStructure service. Represents a row in the &quot;DDMStructure&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.dynamic.data.mapping.model.impl.DDMStructureImpl"
)
@ProviderType
public interface DDMStructure extends DDMStructureModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.dynamic.data.mapping.model.impl.DDMStructureImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMStructure, Long> STRUCTURE_ID_ACCESSOR =
		new Accessor<DDMStructure, Long>() {

			@Override
			public Long get(DDMStructure ddmStructure) {
				return ddmStructure.getStructureId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DDMStructure> getTypeClass() {
				return DDMStructure.class;
			}

		};

	public DDMForm createFullHierarchyDDMForm()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMStructureLayout fetchDDMStructureLayout();

	public java.util.List<String> getChildrenFieldNames(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMForm getDDMForm();

	public DDMFormField getDDMFormField(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMFormField getDDMFormFieldByFieldReference(String fieldReference)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<DDMFormField> getDDMFormFields(
		boolean includeTransientFields);

	public DDMFormLayout getDDMFormLayout()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getDefaultDDMStructureLayoutId();

	public String getFieldDataType(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getFieldLabel(String fieldName, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getFieldLabel(String fieldName, String locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.Set<String> getFieldNames();

	public String getFieldProperty(String fieldName, String property)
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getFieldPropertyByFieldReference(
			String fieldReference, String property)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean getFieldRepeatable(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean getFieldRequired(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getFieldTip(String fieldName, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getFieldTip(String fieldName, String locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getFieldType(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMForm getFullHierarchyDDMForm();

	public java.util.Map<String, DDMFormField> getFullHierarchyDDMFormFieldsMap(
		boolean includeNestedDDMFormFields);

	public DDMStructureVersion getLatestStructureVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<String> getRootFieldNames();

	public DDMStructureVersion getStructureVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<DDMTemplate> getTemplates();

	public String getUnambiguousName(
			java.util.List<DDMStructure> structures, long groupId,
			java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	 * Returns the WebDAV URL to access the structure.
	 *
	 * @param themeDisplay the theme display needed to build the URL. It can
	 set HTTPS access, the server name, the server port, the path
	 context, and the scope group.
	 * @param webDAVToken the WebDAV token for the URL
	 * @return the WebDAV URL
	 */
	public String getWebDavURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay,
		String webDAVToken);

	public boolean hasField(String fieldName);

	public boolean hasFieldByFieldReference(String fieldReference);

	public boolean isFieldRepeatable(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isFieldTransient(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setDDMForm(DDMForm ddmForm);

}