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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput, ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useEffect, useRef, useState} from 'react';

import isClickOutside from '../../utils/clickOutside.es';

const ALL_FORMS = 'all-forms';
const ONLY_THIS_FORM = 'only-this-form';

const LABEL_LEVEL = {
	[ALL_FORMS]: {
		label: Liferay.Language.get('label-for-all-forms-using-this-field'),
	},
	[ONLY_THIS_FORM]: {
		label: Liferay.Language.get('label-for-only-this-form'),
	},
};

export default ({field}) => {
	const popoverRef = useRef(null);
	const triggerRef = useRef(null);
	const [showPopover, setShowPopover] = useState(false);
	const [selectedLabelLevel, setSelectedLabelLevel] = useState(ALL_FORMS);

	useEffect(() => {
		const handler = ({target}) => {
			const outside = isClickOutside(
				target,
				popoverRef?.current,
				triggerRef?.current
			);

			if (outside) {
				setShowPopover(false);
			}
		};

		window.addEventListener('click', handler);

		return () => window.removeEventListener('click', handler);
	}, [popoverRef, triggerRef]);

	return (
		<div className="d-flex form-renderer-label-field justify-content-between">
			<ClayForm.Group className="form-renderer-label-field__input">
				<label className="ddm-label">
					{field.label}

					<span className="ddm-tooltip">
						<ClayIcon
							symbol="question-circle-full"
							title={field.tooltip}
						/>
					</span>
				</label>
				<ClayInput placeholder={field.placeholder} type="text" />
			</ClayForm.Group>

			<ClayTooltipProvider>
				<ClayButtonWithIcon
					alignPosition="bottom-right"
					borderless
					className="form-renderer-label-field__button"
					displayType="secondary"
					onClick={() => setShowPopover(!showPopover)}
					ref={triggerRef}
					small
					symbol="ellipsis-v"
					title={Liferay.Language.get('label-options')}
				/>
			</ClayTooltipProvider>

			<ClayPopover
				alignPosition="bottom-right"
				className="form-renderer-label-field__popover"
				header={Liferay.Language.get('label-options')}
				onShowChange={setShowPopover}
				ref={popoverRef}
				show={showPopover}
			>
				<div className="mt-2">
					<ClayRadioGroup
						onSelectedValueChange={setSelectedLabelLevel}
						selectedValue={selectedLabelLevel}
					>
						{Object.keys(LABEL_LEVEL).map((key) => (
							<ClayRadio
								key={key}
								value={key}
								{...LABEL_LEVEL[key]}
							/>
						))}
					</ClayRadioGroup>
				</div>
			</ClayPopover>
		</div>
	);
};
