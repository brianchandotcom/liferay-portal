/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayTooltipProvider} from '@clayui/tooltip';
import {getTooltipContentRenderer} from '~/routes/customer-portal/containers/ActivationKeysTable/utils/getTooltipContentRenderer';

type ProjectNameTruncateProps = {
	children: string;
};

export const PROJECT_NAME_LIMIT = 16;

const ProjectNameTruncate: React.FC<ProjectNameTruncateProps> = ({
	children,
}) => {
	if (children?.length > PROJECT_NAME_LIMIT) {
		const splittedProjectName = children?.split('', PROJECT_NAME_LIMIT);

		const truncateProjectName = splittedProjectName.join('');

		return (
			<ClayTooltipProvider
				contentRenderer={({title}) => getTooltipContentRenderer(title)}
				delay={100}
			>
				<span>{truncateProjectName}...</span>
			</ClayTooltipProvider>
		);
	}

	return <>{children}</>;
};

export default ProjectNameTruncate;
