/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataApiHelpers} from '../helpers/ApiHelpers';
import {userData} from './performLogin';

export async function addSpaceUser(
	apiHelpers: DataApiHelpers,
	spaceExternalReferenceCode: string,
	roleName: string
): Promise<TUserAccount> {
	const user =
		(await apiHelpers.headlessAdminUser.postUserAccount()) as TUserAccount & {
			externalReferenceCode: string;
		};

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	await apiHelpers.headlessAssetLibrary.putAssetLibraryUserAccount(
		spaceExternalReferenceCode,
		user.externalReferenceCode
	);

	await apiHelpers.headlessAssetLibrary.putAssetLibraryUserAccountRoles(
		spaceExternalReferenceCode,
		user.externalReferenceCode,
		[roleName]
	);

	return user;
}
