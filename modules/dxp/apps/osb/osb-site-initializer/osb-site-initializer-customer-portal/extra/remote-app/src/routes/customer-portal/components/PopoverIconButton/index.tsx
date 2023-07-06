/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayPopover, {ALIGN_POSITIONS} from '@clayui/popover';

type Writeable<T> = {-readonly [P in keyof T]: T[P]};

type PopoverIconButtonProps = {
	alignPosition?: Writeable<typeof ALIGN_POSITIONS[number]>;
	popoverLink?: {textLink: string; url: string};
	popoverText?: string;
};

const PopoverIconButton: React.FC<PopoverIconButtonProps> = ({
	alignPosition = 'bottom',
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
					symbol="info-circle"
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
