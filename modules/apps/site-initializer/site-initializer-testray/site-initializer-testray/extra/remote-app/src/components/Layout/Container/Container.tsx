/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import classNames from 'classnames';
import {ReactNode} from 'react';

type ContainerProps = {
	children: ReactNode;
	className?: string;
	collapsable?: boolean;
	title?: string;
};

const Container: React.FC<ContainerProps> = ({
	children,
	className,
	collapsable = false,
	title,
}) => {
	const containerTitle = <div>{title && <h5>{title}</h5>}</div>;

	if (collapsable) {
		return (
			<ClayPanel
				className="p-4"
				collapsable
				defaultExpanded
				displayTitle={containerTitle}
				displayType="secondary"
				showCollapseIcon
			>
				<ClayPanel.Body>{children}</ClayPanel.Body>
			</ClayPanel>
		);
	}

	return (
		<div
			className={classNames(
				'bg-white border-1 rounded-xs p-4',
				className
			)}
		>
			{containerTitle}

			{children}
		</div>
	);
};

export default Container;
