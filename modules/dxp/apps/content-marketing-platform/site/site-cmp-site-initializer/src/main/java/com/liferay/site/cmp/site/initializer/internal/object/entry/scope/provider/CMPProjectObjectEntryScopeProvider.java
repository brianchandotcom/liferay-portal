/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.object.entry.scope.provider;

import com.liferay.headless.asset.library.dto.v1_0.AssetLibrary;
import com.liferay.headless.asset.library.dto.v1_0.Settings;
import com.liferay.headless.asset.library.resource.v1_0.AssetLibraryResource;
import com.liferay.object.entry.scope.provider.ObjectEntryScopeProvider;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = "object.definition.external.reference.code=L_CMP_PROJECT",
	service = ObjectEntryScopeProvider.class
)
public class CMPProjectObjectEntryScopeProvider
	implements ObjectEntryScopeProvider {

	@Override
	public String getGroupId(User user) throws Exception {
		AssetLibraryResource.Builder builder =
			_assetLibraryResourceFactory.create();

		AssetLibraryResource assetLibraryResource = builder.user(
			user
		).build();

		AssetLibrary assetLibrary = assetLibraryResource.postAssetLibrary(
			new AssetLibrary() {
				{
					setName(StringUtil::randomString);
					setSettings(
						() -> new Settings() {
							{
								setLogoColor(() -> "outline-0");
								setTrashEnabled(() -> false);
							}
						});
					setType(() -> Type.PROJECT);
				}
			});

		return String.valueOf(assetLibrary.getSiteId());
	}

	@Reference
	private AssetLibraryResource.Factory _assetLibraryResourceFactory;

}