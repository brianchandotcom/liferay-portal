/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayPopover, {ALIGN_POSITIONS} from '@clayui/popover';

type Writeable<T> = {-readonly [P in keyof T]: T[P]};

type PopoverIconButtonProps = {
	alignPosition?: Writeable<typeof ALIGN_POSITIONS[number]>;
	isSubscriptionCard?: boolean;
	popoverLink?: {textLink: string; url: string};
	popoverText?: string;
};

const PopoverIconButton: React.FC<PopoverIconButtonProps> = ({
	alignPosition = 'bottom',
	isSubscriptionCard,
	popoverLink,
	popoverText,
}) => {
	return (
		<ClayPopover
			alignPosition={alignPosition}
			closeOnClickOutside
			onClick={(event) => event.stopPropagation()}
			size="lg"
			trigger={
				<ClayButtonWithIcon
					aria-labelledby="Info Icon"
					className="text-brand-primary-darken-2"
					displayType={null}
					onClick={(event) => event.stopPropagation()}
					size="sm"
					symbol={
						isSubscriptionCard ? 'question-circle' : 'info-circle'
					}
				/>
			}
		>
			<p className="font-weight-bold m-0">
				{popoverText}
				&nbsp;
				<a
					href={popoverLink?.url}
					rel="noopener noreferrer"
					target="_blank"
				>
					{popoverLink?.textLink}
				</a>
			</p>
		</ClayPopover>
	);
};

export default PopoverIconButton;
