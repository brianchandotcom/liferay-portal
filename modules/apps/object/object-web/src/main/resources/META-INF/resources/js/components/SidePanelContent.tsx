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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm from '@clayui/form';
import classNames from 'classnames';
import React from 'react';

import './SidePanelContent.scss';

export function closeSidePanel() {
	const parentWindow = Liferay.Util.getOpener();
	parentWindow.Liferay.fire('close-side-panel');
}

export function openToast(options: {
	message: string;
	type?: 'danger' | 'success';
}) {
	const parentWindow = Liferay.Util.getOpener();
	parentWindow.Liferay.Util.openToast(options);
}

export default function SidePanelContent({
	children,
	className,
	onSave,
	readOnly,
	title,
}: IProps) {
	const saveProps: {
		onClick?: () => void;
		type?: 'submit';
	} = onSave ? {onClick: onSave} : {type: 'submit'};

	return (
		<div
			className={classNames('lfr-objects__side-panel-content', className)}
		>
			<div className="lfr-objects__side-panel-content-header">
				<h3 className="mb-0">{title}</h3>

				<ClayButtonWithIcon
					className="side-panel-iframe-close"
					displayType="unstyled"
					monospaced={false}
					onClick={closeSidePanel}
					symbol="times"
				/>
			</div>

			{children}

			<ClayButton.Group
				className="lfr-objects__side-panel-content-container"
				spaced
			>
				<ClayButton displayType="secondary" onClick={closeSidePanel}>
					{Liferay.Language.get('cancel')}
				</ClayButton>

				<ClayButton disabled={readOnly} {...saveProps}>
					{Liferay.Language.get('save')}
				</ClayButton>
			</ClayButton.Group>
		</div>
	);
}

export function SidePanelForm({
	children,
	onSubmit,
	readOnly,
	title,
}: ISidePanelFormProps) {
	return (
		<ClayForm onSubmit={onSubmit}>
			<SidePanelContent readOnly={readOnly} title={title}>
				{children}
			</SidePanelContent>
		</ClayForm>
	);
}

interface IContainerProps {
	children: React.ReactNode;
	className?: string;
}

interface CommonProps extends IContainerProps {
	readOnly?: boolean;
	title: string;
}

interface IProps extends CommonProps {
	onSave?: () => void;
}

interface ISidePanelFormProps extends CommonProps {
	onSubmit?: React.FormEventHandler<HTMLFormElement>;
}
