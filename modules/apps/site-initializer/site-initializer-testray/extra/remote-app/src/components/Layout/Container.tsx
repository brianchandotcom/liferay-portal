/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayPanel from '@clayui/panel';
import classNames from 'classnames';

type ContainerProps = {
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
	const ContainerTitle = () => (
		<div>{title && <h5 className="">{title}</h5>}</div>
	);

	if (collapsable) {
		return (
			<ClayPanel
				className="p-4"
				collapsable
				defaultExpanded
				displayTitle={<ContainerTitle />}
				displayType="secondary"
				showCollapseIcon
			>
				<ClayPanel.Body>{children}</ClayPanel.Body>
			</ClayPanel>
		);
	}

	return (
		<>
			<div
				className={classNames(
					'bg-white border-1 rounded-xs p-4 ',
					className
				)}
			>
				<ContainerTitle />

				{children}
			</div>
		</>
	);
};

export default Container;
