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
import ClayTabs from '@clayui/tabs';
import {fetch} from 'frontend-js-web';
import React, {useContext, useEffect, useState} from 'react';

import {TabsVisitor} from '../../utils/visitor';
import SidePanelContent from '../SidePanelContent';
import InfoScreen from './InfoScreen/InfoScreen';
import LayoutScreen from './LayoutScreen/LayoutScreen';
import LayoutContext, {LayoutContextProvider, TYPES} from './context';
import {
	TObjectField,
	TObjectLayout,
	TObjectLayoutTab,
	TObjectRelationship,
} from './types';

const TABS = [
	{
		Component: InfoScreen,
		label: Liferay.Language.get('info'),
	},
	{
		Component: LayoutScreen,
		label: Liferay.Language.get('layout'),
	},
];

const HEADERS = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

type TNormalizeObjectFields = ({
	objectFields,
	objectLayout,
}: {
	objectFields: TObjectField[];
	objectLayout: TObjectLayout;
}) => TObjectField[];

const normalizeObjectFields: TNormalizeObjectFields = ({
	objectFields,
	objectLayout,
}) => {
	const visitor = new TabsVisitor(objectLayout);
	const objectFieldIds = objectFields.map(({id}) => id);

	const normalizedObjectFields = [...objectFields];

	visitor.mapFields((field) => {
		const objectFieldIndex = objectFieldIds.indexOf(field.objectFieldId);
		normalizedObjectFields[objectFieldIndex].inLayout = true;
	});

	return normalizedObjectFields;
};

type TNormalizeObjectRelationships = ({
	objectLayoutTabs,
	objectRelationships,
}: {
	objectLayoutTabs: TObjectLayoutTab[];
	objectRelationships: TObjectRelationship[];
}) => TObjectRelationship[];

const normalizeObjectRelationships: TNormalizeObjectRelationships = ({
	objectLayoutTabs,
	objectRelationships,
}) => {
	const objectRelationshipIds = objectRelationships.map(({id}) => id);

	const normalizedObjectRelationships = [...objectRelationships];

	objectLayoutTabs.forEach(({objectRelationshipId}) => {
		if (objectRelationshipId) {
			const objectRelationshipIndex = objectRelationshipIds.indexOf(
				objectRelationshipId
			);

			normalizedObjectRelationships[
				objectRelationshipIndex
			].inLayout = true;
		}
	});

	return normalizedObjectRelationships;
};

const Layout: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const [
		{isViewOnly, objectFields, objectLayout, objectLayoutId},
		dispatch,
	] = useContext(LayoutContext);
	const [activeIndex, setActiveIndex] = useState<number>(0);
	const [loading, setLoading] = useState<boolean>(true);

	const onCloseSidePanel = () => {
		const parentWindow = Liferay.Util.getOpener();

		parentWindow.Liferay.fire('close-side-panel');
	};

	useEffect(() => {
		const makeFetch = async () => {
			const objectLayoutResponse = await fetch(
				`/o/object-admin/v1.0/object-layouts/${objectLayoutId}`,
				{
					headers: HEADERS,
					method: 'GET',
				}
			);

			const {
				defaultObjectLayout,
				name,
				objectDefinitionId,
				objectLayoutTabs,
			} = (await objectLayoutResponse.json()) as any;

			const objectFieldsResponse = await fetch(
				`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/object-fields`,
				{
					headers: HEADERS,
					method: 'GET',
				}
			);

			const objectRelationshipsResponse = await fetch(
				`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/object-relationships`,
				{
					headers: HEADERS,
					method: 'GET',
				}
			);

			const objectLayout = {
				defaultObjectLayout,
				name,
				objectLayoutTabs,
			};

			dispatch({
				payload: {
					objectLayout,
				},
				type: TYPES.ADD_OBJECT_LAYOUT,
			});

			const {
				items: objectFields,
			}: {
				items: TObjectField[];
			} = (await objectFieldsResponse.json()) as any;

			dispatch({
				payload: {
					objectFields: normalizeObjectFields({
						objectFields,
						objectLayout,
					}),
				},
				type: TYPES.ADD_OBJECT_FIELDS,
			});

			const {
				items: objectRelationships,
			}: {
				items: TObjectRelationship[];
			} = (await objectRelationshipsResponse.json()) as any;

			dispatch({
				payload: {
					objectRelationships: normalizeObjectRelationships({
						objectLayoutTabs,
						objectRelationships,
					}),
				},
				type: TYPES.ADD_OBJECT_RELATIONSHIPS,
			});

			setLoading(false);
		};

		makeFetch();
	}, [objectLayoutId, dispatch]);

	const saveObjectLayout = async () => {
		const hasFieldsInLayout = objectFields.some(
			(objectField) => objectField.inLayout
		);

		if (!hasFieldsInLayout) {
			Liferay.Util.openToast({
				message: Liferay.Language.get('please-add-at-least-one-field'),
				type: 'danger',
			});

			return;
		}

		const response = await fetch(
			`/o/object-admin/v1.0/object-layouts/${objectLayoutId}`,
			{
				body: JSON.stringify(objectLayout),
				headers: HEADERS,
				method: 'PUT',
			}
		);

		if (response.status === 401) {
			window.location.reload();
		}
		else if (response.ok) {
			Liferay.Util.openToast({
				message: Liferay.Language.get(
					'the-object-layout-was-updated-successfully'
				),
				type: 'success',
			});

			setTimeout(() => {
				const parentWindow = Liferay.Util.getOpener();
				parentWindow.Liferay.fire('close-side-panel');
			}, 1500);
		}
		else {
			const {
				title = Liferay.Language.get('an-error-occurred'),
			} = (await response.json()) as {title: any};

			Liferay.Util.openToast({
				message: title,
				type: 'danger',
			});
		}
	};

	return (
		<>
			<ClayTabs className="side-panel-iframe__tabs">
				{TABS.map(({label}, index) => (
					<ClayTabs.Item
						active={activeIndex === index}
						key={index}
						onClick={() => setActiveIndex(index)}
					>
						{label}
					</ClayTabs.Item>
				))}
			</ClayTabs>

			<SidePanelContent className="side-panel-content--layout">
				<SidePanelContent.Body>
					<ClayTabs.Content activeIndex={activeIndex} fade>
						{TABS.map(({Component}, index) => (
							<ClayTabs.TabPane key={index}>
								{!loading && <Component />}
							</ClayTabs.TabPane>
						))}
					</ClayTabs.Content>
				</SidePanelContent.Body>

				{!loading && (
					<SidePanelContent.Footer>
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onCloseSidePanel}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={isViewOnly}
								onClick={() => saveObjectLayout()}
							>
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					</SidePanelContent.Footer>
				)}
			</SidePanelContent>
		</>
	);
};

interface ILayoutWrapperProps extends React.HTMLAttributes<HTMLElement> {
	isViewOnly: boolean;
	objectFieldTypes: ObjectFieldType[];
	objectLayoutId: string;
}

const LayoutWrapper: React.FC<ILayoutWrapperProps> = ({
	isViewOnly,
	objectFieldTypes,
	objectLayoutId,
}) => {
	return (
		<LayoutContextProvider
			value={{
				isViewOnly,
				objectFieldTypes,
				objectLayoutId,
			}}
		>
			<Layout />
		</LayoutContextProvider>
	);
};

export default LayoutWrapper;
