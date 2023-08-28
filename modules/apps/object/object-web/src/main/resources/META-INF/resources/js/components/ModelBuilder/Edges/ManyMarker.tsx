/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export const manyMarkerId = 'manyMarker';

export default function ManyMarker() {
	return (
		<defs>
			<marker
				id={manyMarkerId}
				markerHeight="10"
				markerWidth="22"
				orient="auto"
				refX="13"
				refY="9"
				viewBox="0 0 9 19"
			>
				<path
					clipRule="evenodd"
					d="M12.4788 17.7526C12.0632 18.1162 11.4314 18.0741 11.0677 17.6585L4.06772 9.65849C3.73783 9.28146 3.73783 8.71851 4.06772 8.34148L11.0677 0.341482C11.4314 -0.074154 12.0632 -0.116272 12.4788 0.24741C12.8944 0.611092 12.9366 1.24286 12.5729 1.65849L6.14907 8.99999L12.5729 16.3415C12.9366 16.7571 12.8944 17.3889 12.4788 17.7526Z"
					fillRule="evenodd"
				/>

				<rect height="18" rx="1" width="2" x="0.820312" />
			</marker>
		</defs>
	);
}
