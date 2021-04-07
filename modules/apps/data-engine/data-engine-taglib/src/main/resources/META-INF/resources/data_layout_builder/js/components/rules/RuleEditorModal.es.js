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
import {PagesVisitor} from 'data-engine-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useMemo, useRef, useState} from 'react';

import {Editor} from './editor/Editor.es';

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

	visitor.mapFields(
		(field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
			if (field.type != 'fieldset') {
				fields.push({
					...field,
					pageIndex,
					value: field.fieldName,
				});
			}
		},
		true,
		true
	);

	return fields;
}

const RuleEditorModalContent = ({
	functionsMetadata,
	functionsURL,
	onClick,
	onClose,
	pages,
	rule,
}) => {
	const [invalidRule, setInvalidRule] = useState(true);

	const [ruleName, setRuleName] = useState(
		rule?.name[themeDisplay.getDefaultLanguageId()]
	);

	/**
	 * This reference is used for updating rule value without causing a re-render
	 */
	const ruleRef = useRef(rule);

	const {resource: rolesResource} = useResource({
		fetch,
		link: `${window.location.origin}/o/headless-admin-user/v1.0/roles`,
	});

	const roles = useMemo(
		() =>
			rolesResource?.items.map(({id, name}) => ({
				id: `${id}`,
				label: name,
				name,
				value: name,
			})),
		[rolesResource]
	);

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
					onValidator={(value) => setInvalidRule(!value)}
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
							onClick={() => {
								onClick({
									dataRule: {
										...ruleRef.current,
										name: ruleName,
									},
									loc: rule?.ruleEditedIndex,
									rule,
								});
								onClose();
							}}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
};

export default ({
	functionsMetadata,
	functionsURL,
	isVisible,
	onClick,
	onClose: onCloseFn,
	pages,
	rule,
}) => {
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
			<RuleEditorModalContent
				functionsMetadata={functionsMetadata}
				functionsURL={functionsURL}
				onClick={onClick}
				onClose={onClose}
				pages={pages}
				rule={rule}
			/>
		</ClayModal>
	);
};
