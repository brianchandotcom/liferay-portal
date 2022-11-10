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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';

import i18n from '../../i18n';

type ButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement> & {
	loading?: boolean;
};

type FloatingBoxProps = {
	alerts?: {text: string; title?: string}[];
	isVisible: boolean;
	onClear?: () => void;
	onSubmit?: () => void;
	primaryButtonProps?: ButtonProps;
	selectdCount?: number;
	tooltipText: string;
};

const FloatingBox: React.FC<FloatingBoxProps> = ({
	alerts,
	isVisible,
	onClear,
	onSubmit,
	selectdCount,
	primaryButtonProps: {loading, ...primaryButtonProps} = {},
	tooltipText,
}) => {
	return (
		<div
			className={classNames('testray-floating-box', {
				'box-hidden': !isVisible,
				'box-visible': isVisible,
			})}
		>
			<>
				{alerts?.map((item, index) => {
					const {text, title} = item;

					return (
						<div className="alert" key={index}>
							<ClayAlert
								displayType="danger"
								key={index}
								title={title}
								variant="feedback"
							>
								<span className="ml-1">{text}</span>
							</ClayAlert>
						</div>
					);
				})}

				<div className="align-items d-flex justify-content-between m-3">
					<div className="d-flex label-selected">
						<span className="mr-2 selectdCount">
							{selectdCount}
						</span>

						<span className="mb-0">
							{i18n.translate('selected')}
						</span>
					</div>

					<div className="d-flex flex-row">
						<ClayTooltipProvider>
							<ClayButton
								className="mr-1"
								displayType="secondary"
								onClick={() => onClear}
								title="Deselect Items"
							>
								{i18n.translate('clear')}
							</ClayButton>
						</ClayTooltipProvider>

						<ClayTooltipProvider>
							<ClayButton
								{...primaryButtonProps}
								disabled={
									primaryButtonProps?.disabled || loading
								}
								displayType="primary"
								onClick={onSubmit}
								title={tooltipText}
							>
								{i18n.translate(
									primaryButtonProps?.title as string
								)}
							</ClayButton>
						</ClayTooltipProvider>
					</div>
				</div>
			</>
		</div>
	);
};

export default FloatingBox;
