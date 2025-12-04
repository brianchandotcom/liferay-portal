import DisplayComponent from './display-components';
import React, {Fragment, useContext} from 'react';
import {ConjunctionKey, SegmentTypes} from 'shared/util/constants';
import {Criteria} from 'segment/segment-editor/dynamic/utils/types';
import {findPropertyByCriterion} from 'segment/segment-editor/dynamic/utils/utils';
import {ReferencedObjectsContext} from 'segment/segment-editor/dynamic/context/referencedObjects';

interface ICriteriaViewProps extends React.HTMLAttributes<HTMLDivElement> {
	criteria: Criteria;
	forwardedRef?: React.Ref<any>;
	segmentType: SegmentTypes;
	timeZoneId: string;
}

const CONJUNCTION_MAP = {
	[ConjunctionKey.And]: Liferay.Language.get('and'),
	[ConjunctionKey.Or]: Liferay.Language.get('or')
};

const CriteriaView: React.FC<ICriteriaViewProps> = ({
	criteria,
	forwardedRef,
	segmentType,
	timeZoneId
}) => {
	const {referencedProperties} = useContext(ReferencedObjectsContext);

	const renderCriteriaGroup = criteria => {
		const {conjunctionName, criteriaGroupId, items} = criteria;

		return (
			<div className='criteria-group' key={criteriaGroupId}>
				{items.map((criterion, index) => (
					<Fragment key={index}>
						{index !== 0 && (
							<div className='conjunction'>
								{CONJUNCTION_MAP[conjunctionName]}
							</div>
						)}

						{criterion.items
							? renderCriteriaGroup(criterion)
							: renderCriteriaRow(criterion)}
					</Fragment>
				))}
			</div>
		);
	};

	const renderCriteriaRow = criterion => {
		const property = findPropertyByCriterion(
			criterion,
			referencedProperties
		);

		return (
			<div className='criteria-row'>
				{property ? (
					<DisplayComponent
						criterion={criterion}
						property={property}
						segmentType={segmentType}
						timeZoneId={timeZoneId}
					/>
				) : (
					<b className='undefined-property'>
						{Liferay.Language.get('attribute-no-longer-exists')}
					</b>
				)}
			</div>
		);
	};

	return (
		<div className='criteria-view-root pt-2' ref={forwardedRef}>
			{renderCriteriaGroup(criteria)}
		</div>
	);
};

export default React.forwardRef<HTMLDivElement, ICriteriaViewProps>(
	(props, ref) => <CriteriaView forwardedRef={ref} {...props} />
);
