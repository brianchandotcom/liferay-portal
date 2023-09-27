/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function findPageContent(pageContents, item) {
	return pageContents?.find(
		({classNameId, classPK, externalReferenceCode}) => {
			if (classNameId !== item.classNameId) {
				return false;
			}

			return (
				(classPK && classPK === item.classPK) ||
				(externalReferenceCode &&
					externalReferenceCode === item.externalReferenceCode)
			);
		}
	);
}
