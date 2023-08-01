/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ProjectCategoryDropdown from './components/ProjectCategoryDropdown/ProjectCategoryDropdown';

const ProjectsNavbar = ({
	loading,
	onSelect,
	projectCategory,
	selectedProjectCategory,
}) => (
	<div>
		<ProjectCategoryDropdown
			loading={loading}
			onSelect={onSelect}
			projectCategory={projectCategory}
			selectedProjectCategory={selectedProjectCategory}
		/>
	</div>
);

export default ProjectsNavbar;
