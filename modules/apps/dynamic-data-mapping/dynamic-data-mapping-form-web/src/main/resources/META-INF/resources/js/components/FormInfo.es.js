/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useConfig, useForm, useFormState} from 'data-engine-js-components-web';
import {autoSize} from 'frontend-js-web';
import React, {useEffect, useRef} from 'react';

import {EVENT_TYPES} from '../eventTypes.es';
import {isKeyInSet, isModifyingKey} from '../util/dom';

const isForbiddenKey = (event, limit) => {
	const charCode = event.which ? event.which : event.keyCode;
	let forbidden = false;

	if (
		event.target.innerText.length >= limit &&
		isModifyingKey(charCode) &&
		!isKeyInSet(charCode, ['BACKSPACE', 'DELETE', 'ESC', 'ENTER'])
	) {
		forbidden = true;
	}

	return forbidden;
};

export function FormInfo() {
	const {portletNamespace} = useConfig();

	const {
		defaultLanguageId,
		editingLanguageId,
		localizedDescription,
		localizedName,
	} = useFormState();
	const dispatch = useForm();

	const nameRef = useRef(null);
	const descriptionRef = useRef(null);

	useEffect(() => {
		if (nameRef.current && descriptionRef.current) {
			new autoSize(nameRef.current);
			new autoSize(descriptionRef.current);
		}
	}, []);

	const onKeyDown = (event) => {
		const charCode = event.which ? event.which : event.keyCode;

		if (isForbiddenKey(event, 120) && Number(charCode) !== 91) {
			event.preventDefault();
		}
	};

	return (
		<div className="ddm-form-basic-info">
			<ClayLayout.ContainerFluid size="xl">
				<h1>
					<ClayForm.Group>
						<textarea
							className="ddm-form-name form-control"
							id="nameEditor"
							name={`${portletNamespace}nameEditor`}
							onChange={(event) =>
								dispatch({
									payload: event.target.value,
									type: EVENT_TYPES.FORM_INFO.NAME_CHANGE,
								})
							}
							onKeyDown={onKeyDown}
							placeholder={Liferay.Language.get('untitled-form')}
							ref={nameRef}
							style={{height: '100px'}}
							value={
								localizedName[editingLanguageId] ??
								localizedName[defaultLanguageId]
							}
						/>
					</ClayForm.Group>
				</h1>

				<h5>
					<ClayForm.Group>
						<textarea
							className="ddm-form-description form-control"
							id="descriptionEditor"
							name={`${portletNamespace}descriptionEditor`}
							onChange={(event) =>
								dispatch({
									payload: event.target.value,
									type:
										EVENT_TYPES.FORM_INFO
											.DESCRIPTION_CHANGE,
								})
							}
							placeholder={Liferay.Language.get(
								'add-a-short-description-for-this-form'
							)}
							ref={descriptionRef}
							style={{height: '24px'}}
							value={
								localizedDescription[editingLanguageId] ??
								localizedDescription[defaultLanguageId]
							}
						/>
					</ClayForm.Group>
				</h5>
			</ClayLayout.ContainerFluid>
		</div>
	);
}
