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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import EditAppContext from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import {sub} from 'app-builder-web/js/utils/lang.es';
import React, {useContext, useState} from 'react';

import IconWithPopover from '../../../../components/icon-with-popover/IconWithPopover.es';
import SelectDropdown from '../../../../components/select-dropdown/SelectDropdown.es';
import {DataAndViewsTabContext, OpenButton} from './DataAndViewsTab.es';

const Item = ({
	id,
	missingRequiredFields: {missing: missingField = false, nativeField} = {},
	name,
}) => {
	const {
		config: {dataObject},
	} = useContext(EditAppContext);
	const [showPopover, setShowPopover] = useState(false);

	const {custom, native} = {
		custom: {
			triggerProps: {
				className: 'help-cursor info tooltip-popover-icon',
				fontSize: '26px',
				symbol: 'info-circle',
			},
		},
		native: {
			popoverProps: {
				onMouseEnter: () => setShowPopover(true),
				onMouseLeave: () => setShowPopover(false),
			},
			triggerProps: {
				className: 'error help-cursor tooltip-popover-icon',
				fontSize: '26px',
				symbol: 'exclamation-full',
			},
		},
	};

	return (
		<>
			<span
				className="float-left text-left text-truncate w50"
				title={name}
			>
				{name}
			</span>

			{missingField && (
				<IconWithPopover
					className="dropdown-popover-form-view"
					header={<PopoverHeader nativeField={nativeField} />}
					popoverProps={nativeField && {...native.popoverProps}}
					show={showPopover}
					trigger={
						<div className="dropdown-button-asset help-cursor">
							<IconWithPopover.TriggerIcon
								iconProps={
									nativeField
										? native.triggerProps
										: custom.triggerProps
								}
								onMouseEnter={() => setShowPopover(true)}
								onMouseLeave={() => setShowPopover(false)}
								onMouseOver={() => setShowPopover(true)}
							/>
						</div>
					}
				>
					<PopoverContent
						buttonProps={{onClick: () => setShowPopover(false)}}
						dataObject={dataObject}
						id={id}
						nativeField={nativeField}
					/>
				</IconWithPopover>
			)}
		</>
	);
};

const PopoverContent = ({
	buttonProps: {onClick},
	dataObject: {defaultLanguageId, id, name},
	id: formViewId,
	nativeField,
}) => {
	const {openFormViewModal, updateFormView} = useContext(
		DataAndViewsTabContext
	);

	const {custom, native} = {
		custom: {
			content: sub(
				Liferay.Language.get('this-form-view-must-include-all-fields'),
				[name]
			),
		},
		native: {
			content: (
				<>
					{sub(
						Liferay.Language.get(
							'this-form-view-does-not-contain-all-required-fields-and-cannot-be-used'
						),
						[name]
					)}

					<ClayButton
						className="mt-3"
						displayType="secondary"
						onClick={() => {
							onClick();

							openFormViewModal(
								id,
								defaultLanguageId,
								updateFormView,
								formViewId
							);
						}}
					>
						<span className="text-secondary">
							{Liferay.Language.get('edit-form-view')}
						</span>
					</ClayButton>
				</>
			),
		},
	};

	return nativeField ? native.content : custom.content;
};

const PopoverHeader = ({nativeField}) => {
	return (
		<>
			{!nativeField && (
				<ClayIcon className="mr-1 text-info" symbol="info-circle" />
			)}

			<span>{Liferay.Language.get('missing-required-fields')}</span>
		</>
	);
};

function SelectFormView({openButtonProps, showWarningIcon, ...props}) {
	props = {
		...props,
		emptyResultMessage: Liferay.Language.get(
			'no-form-views-were-found-with-this-name-try-searching-again-with-a-different-name'
		),
		label: Liferay.Language.get('select-a-form-view'),
		stateProps: {
			emptyProps: {
				label: Liferay.Language.get('there-are-no-form-views-yet'),
			},
			loadingProps: {
				label: Liferay.Language.get('retrieving-all-form-views'),
			},
		},
		warningIcon: showWarningIcon
			? {
					className: 'info mr-2 tooltip-popover-icon',
					fontSize: '26px',
					symbol: 'info-circle',
			  }
			: undefined,
	};

	return (
		<div className="d-flex">
			<SelectDropdown {...props} />

			<OpenButton {...openButtonProps} />
		</div>
	);
}

SelectFormView.Item = Item;

export default SelectFormView;
