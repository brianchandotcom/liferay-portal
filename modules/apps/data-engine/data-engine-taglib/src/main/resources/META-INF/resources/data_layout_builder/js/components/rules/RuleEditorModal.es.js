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

import ClayButton from '@clayui/button';
import {useResource} from '@clayui/data-provider';
import {ClayInput} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {fetch} from 'frontend-js-web';
import React, {useContext, useMemo, useRef, useState} from 'react';

import AppContext from '../../AppContext.es';
import {ADD_DATA_LAYOUT_RULE, UPDATE_DATA_LAYOUT_RULE} from '../../actions.es';
import DataLayoutBuilderContext from '../../data-layout-builder/DataLayoutBuilderContext.es';
import ModalWithEventPrevented from '../modal/ModalWithEventPrevented.es';
import {Editor} from '../rule-builder/editor/Editor.es';

function getTransformedPages(pages) {
	return pages.map(({title}, index) => ({
		label: `${index + 1} ${title || Liferay.Language.get('page-title')}`,
		name: index.toString(),
		value: index.toString(),
	}));
}

function getFields(pages) {
	const fields = [];
	const visitor = new PagesVisitor(pages);

	visitor.mapFields((field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
		if (field.type != 'fieldset') {
			fields.push({
				...field,
				pageIndex,
				value: field.fieldName,
			});
		}
	});

	return fields;
}

function getFormattedRoles(roles) {
	return roles.map(({id, name}) => ({
		id: `${id}`,
		label: name,
		name,
		value: name,
	}));
}

const RuleEditorModalContent = ({onClose, rule}) => {
	const [invalidRule, setInvalidRule] = useState(true);

	const [ruleName, setRuleName] = useState(rule?.name[themeDisplay.getDefaultLanguageId()]);

	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);
	const {pages} = dataLayoutBuilder.getStore();

	const [
		{
			config: {
				ruleSettings: {functionsMetadata, functionsURL},
			},
		},
		dispatch,
	] = useContext(AppContext);

	/**
	 * This reference is used for updating rule value without causing a re-render
	 */
	const ruleRef = useRef(rule);

	const {resource: rolesResource} = useResource({
		fetch,
		link: `${window.location.origin}/o/headless-admin-user/v1.0/roles`,
	});

	const roles = useMemo(() => {
		if (rolesResource?.items?.roles) {
			return getFormattedRoles(rolesResource?.items?.roles);
		}

		return [];
	}, [rolesResource]);

	const transformedPages = useMemo(() => getTransformedPages(pages), [pages]);
	const fields = useMemo(() => getFields(pages), [pages]);

	return (
		<>
			<ClayModal.Header>
				{rule
					? Liferay.Language.get('edit-rule')
					: Liferay.Language.get('create-new-rule')}
			</ClayModal.Header>
			<ClayModal.Header withTitle={false}>
				<ClayInput.Group className="pl-4 pr-4">
					<ClayInput.GroupItem>
						<ClayInput
							aria-label={Liferay.Language.get('rule-title')}
							className="form-control-inline"
							onChange={({target: {value}}) => setRuleName(value)}
							placeholder={Liferay.Language.get('untitled-rule')}
							type="text"
							value={ruleName}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayModal.Header>
			<ClayModal.Body>
				<Editor
					allowActions={[
						'show',
						'enable',
						'require',
						'calculate',
						'jump-to-page',
					]}
					className="pl-4 pr-4"
					dataProvider={[]}
					fields={fields}
					functionsURL={functionsURL}
					onChange={(rule) => {
						ruleRef.current = rule;
					}}
					onValidator={setInvalidRule}
					operatorsByType={functionsMetadata}
					pages={transformedPages}
					roles={roles}
					rule={rule}
				/>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton
							disabled={invalidRule || !ruleName}
							onClick={() =>
								dispatch({
									payload: {dataRule: ruleRef.current},
									type:
										rule
											? UPDATE_DATA_LAYOUT_RULE
											: ADD_DATA_LAYOUT_RULE,
								})
							}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
};

const RuleEditorModal = ({isVisible, onClose: onCloseFn, rule}) => {
	const {observer, onClose} = useModal({
		onClose: onCloseFn,
	});

	if (!isVisible) {
		return null;
	}

	return (
		<ClayModal
			className="data-layout-builder-editor-modal"
			observer={observer}
			size="full-screen"
		>
			<RuleEditorModalContent onClose={onClose} rule={rule} />
		</ClayModal>
	);
};

export default (props) => (
	<ModalWithEventPrevented>
		<RuleEditorModal {...props} />
	</ModalWithEventPrevented>
);
