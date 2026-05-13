import CriteriaView from './CriteriaView';
import Label from 'shared/components/Label';
import Panel from '@clayui/panel';
import React, {useContext, useEffect, useMemo} from 'react';
import {
	createTagProperty,
	createVocabularyProperty
} from 'segment/segment-editor/dynamic/utils/utils';
import {
	CustomFunctionOperators,
	NotOperators
} from 'segment/segment-editor/dynamic/utils/constants';
import {ReferencedObjectsContext} from 'segment/segment-editor/dynamic/context/referencedObjects';
import {ReportContainer} from 'shared/components/download-report/DownloadPDFReport';
import {SegmentTypes} from 'shared/util/constants';
import {translateQueryToCriteria} from 'segment/segment-editor/dynamic/utils/odata';
import {useDownloadReportContext} from 'shared/components/download-report/DownloadReportContext';

const VOCABULARY_OPERATORS = new Set([
	CustomFunctionOperators.VocabulariesFilter,
	NotOperators.NotVocabulariesFilter
]);

const TAG_OPERATORS = new Set([
	CustomFunctionOperators.TagsFilter,
	NotOperators.NotTagsFilter
]);

function extractVocabularies(criteria: any): Array<{id: string; name: string}> {
	if (!criteria) return [];

	if (criteria.items) {
		return criteria.items.flatMap(extractVocabularies);
	}

	if (
		criteria.propertyName &&
		VOCABULARY_OPERATORS.has(criteria.operatorName)
	) {
		const id = criteria.propertyName;
		const items = criteria.value?.getIn?.(['criterionGroup', 'items']);
		const nameItem = items?.find?.(
			(item: any) => item.get?.('propertyName') === 'vocabularies/name'
		);
		const name =
			(nameItem?.get?.('value') as string) ??
			(criteria.value?.get?.('_name') as string) ??
			id;

		return [{id, name}];
	}

	return [];
}

function extractTags(criteria: any): Array<{id: string; name: string}> {
	if (!criteria) return [];

	if (criteria.items) {
		return criteria.items.flatMap(extractTags);
	}

	if (criteria.propertyName && TAG_OPERATORS.has(criteria.operatorName)) {
		const id = criteria.propertyName;
		const items = criteria.value?.getIn?.(['criterionGroup', 'items']);
		const nameItem = items?.find?.(
			(item: any) => item.get?.('propertyName') === 'tags/name'
		);
		const name =
			(nameItem?.get?.('value') as string) ??
			(criteria.value?.get?.('_name') as string) ??
			id;

		return [{id, name}];
	}

	return [];
}

interface ICriteriaCardProps {
	channelId?: string;
	criteriaString: string;
	groupId?: string;
	includeAnonymousUsers: boolean;
	segmentType: SegmentTypes;
	sequential: boolean;
	timeZoneId: string;
}

const CriteriaCard: React.FC<ICriteriaCardProps> = ({
	channelId,
	criteriaString,
	groupId,
	includeAnonymousUsers,
	segmentType,
	sequential,
	timeZoneId
}) => {
	const _criteriaViewRef = React.createRef<HTMLDivElement>();

	const {clearReportContainers, setReportContainer} =
		useDownloadReportContext();

	const {addProperty} = useContext(ReferencedObjectsContext);

	const criteria = useMemo(
		() => translateQueryToCriteria(criteriaString),
		[criteriaString]
	);

	useEffect(() => {
		setReportContainer(ReportContainer.SegmentCriteriaCard);

		return clearReportContainers;
	}, []);

	useEffect(() => {
		if (!channelId || !groupId || !addProperty) return;

		const vocabularies = extractVocabularies(criteria);

		if (vocabularies.length) {
			vocabularies.forEach(({id, name}) => {
				addProperty(createVocabularyProperty({id, name}));
			});
		}

		const tags = extractTags(criteria);

		if (tags.length) {
			tags.forEach(({id, name}) => {
				addProperty(createTagProperty({id, name}));
			});
		}
	}, [channelId, groupId, criteria]);

	return (
		<Panel
			className='card-root'
			collapsable
			defaultExpanded
			displayTitle={
				<Panel.Title className='card-title'>
					{Liferay.Language.get('segment-criteria')}
				</Panel.Title>
			}
			id={ReportContainer.SegmentCriteriaCard}
		>
			<Panel.Body className='criteria-card-root'>
				{includeAnonymousUsers && (
					<Label display='info' size='lg' uppercase>
						{Liferay.Language.get('includes-anonymous-individuals')}
					</Label>
				)}

				{segmentType === SegmentTypes.RealTime && sequential && (
					<Label display='info' size='lg' uppercase>
						{Liferay.Language.get('sequential-events')}
					</Label>
				)}

				{criteria && (
					<CriteriaView
						criteria={criteria}
						ref={_criteriaViewRef}
						segmentType={segmentType}
						sequential={sequential}
						timeZoneId={timeZoneId}
					/>
				)}
			</Panel.Body>
		</Panel>
	);
};

export default CriteriaCard;
