/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the FragmentComposition service. Represents a row in the &quot;FragmentComposition&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCompositionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.fragment.model.impl.FragmentCompositionImpl"
)
@ProviderType
public interface FragmentComposition
	extends FragmentCompositionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.fragment.model.impl.FragmentCompositionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FragmentComposition, Long>
		FRAGMENT_COMPOSITION_ID_ACCESSOR =
			new Accessor<FragmentComposition, Long>() {

				@Override
				public Long get(FragmentComposition fragmentComposition) {
					return fragmentComposition.getFragmentCompositionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<FragmentComposition> getTypeClass() {
					return FragmentComposition.class;
				}

			};

	public com.liferay.portal.kernel.json.JSONObject getDataJSONObject()
		throws Exception;

	public String getIcon();

	public String getImagePreviewURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay);

	public void populateZipWriter(
			com.liferay.portal.kernel.zip.ZipWriter zipWriter, String path)
		throws Exception;

	public void setIcon(String icon);

	public void setImagePreviewURL(String imagePreviewURL);

}