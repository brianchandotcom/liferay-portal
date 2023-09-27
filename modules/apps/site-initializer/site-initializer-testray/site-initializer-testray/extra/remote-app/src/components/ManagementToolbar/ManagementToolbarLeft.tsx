/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type ManagementToolbarLeftProps = {
	title?: string;
};

const ManagementToolbarLeft: React.FC<ManagementToolbarLeftProps> = ({
	title,
}) => {
	if (title) {
		return <h5 className="ml-2">{title}</h5>;
	}

	return <div />;
};

export default ManagementToolbarLeft;
