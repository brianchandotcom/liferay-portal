import Card from 'shared/components/Card';
import CodeSnippet from 'shared/components/CodeSnippet';
import Label from 'shared/components/form/Label';
import React, {useEffect, useState} from 'react';
import Table from 'shared/components/table';
import {Attribute} from 'event-analysis/utils/types';
import {attributeListColumns} from 'shared/util/table-columns';
import {Map, OrderedMap} from 'immutable';

interface IEventDetailsCardProps {
	eventAttributes: Attribute[];
	eventName: string;
	groupId: string;
}

const EventDetailsCard: React.FC<IEventDetailsCardProps> = ({
	eventAttributes,
	eventName,
	groupId
}) => {
	const [codeLines, setCodeLines] = useState([
		`Analytics.track('${eventName}', {`,
		'});'
	]);

	const [selectedAttributes, setSelectedAttributes] = useState(
		OrderedMap<string, Map<string, any>>(
			eventAttributes.map(
				attribute =>
					[attribute.id, Map(attribute)] as [
						string,
						Map<string, string>
					]
			)
		)
	);

	useEffect(() => {
		const attributesRepresentations = [];

		selectedAttributes.forEach(attribute => {
			const name = attribute.get('name');
			const sampleValue = attribute.get('sampleValue');

			attributesRepresentations.push(`'${name}': '${sampleValue}',`);
		});

		setCodeLines([
			codeLines[0],
			...attributesRepresentations,
			codeLines[codeLines.length - 1]
		]);
	}, [selectedAttributes]);

	const addSelectedAttribute = (attribute: Attribute): void =>
		setSelectedAttributes(
			selectedAttributes.set(attribute.id, Map(attribute))
		);

	const removeSelectedAttribute = (key: string): void => {
		setSelectedAttributes(previous => previous.remove(key));
	};

	return (
		<Card key='cardContainer'>
			<Card.Header>
				<Card.Title>
					{Liferay.Language.get('send-this-event')}
				</Card.Title>
			</Card.Header>

			<Card.Body>
				<span className='mt-2 mb-4 w-50'>
					{Liferay.Language.get(
						'use-this-script-to-start-sending-events-to-analytics-cloud.-you-can-customize-which-attributes-to-send-with-a-specific-event.-selecting-the-attributes-below-will-generate-a-new-sample-script'
					)}
				</span>

				<Label>{Liferay.Language.get('sample-javascript-colon')}</Label>

				<CodeSnippet codeLines={codeLines} />
			</Card.Body>

			<Table
				className='mb-0'
				columns={[
					attributeListColumns.getName({
						channelId: 'channelId',
						groupId
					}),
					attributeListColumns.displayName,
					attributeListColumns.description,
					attributeListColumns.sampleValue,
					attributeListColumns.dataType
				].map(column => ({...column, sortable: false}))}
				items={eventAttributes}
				onSelectItemsChange={selectedAttribute =>
					selectedAttributes.has(selectedAttribute.id)
						? removeSelectedAttribute(selectedAttribute.id)
						: addSelectedAttribute(selectedAttribute as Attribute)
				}
				rowIdentifier='id'
				selectedItemsIOMap={selectedAttributes}
				showCheckbox
			/>
		</Card>
	);
};

export default EventDetailsCard;
