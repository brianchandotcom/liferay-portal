import DisplayComponent from './display-components';
import React, {Fragment} from 'react';
import {ConjunctionKey} from 'shared/util/constants';
import {Criteria} from 'segment/segment-editor/dynamic/utils/types';
import {findPropertyByCriterion} from 'segment/segment-editor/dynamic/utils/utils';
import {ReferencedObjectsContext} from 'segment/segment-editor/dynamic/context/referencedObjects';

interface ICriteriaViewProps extends React.HTMLAttributes<HTMLDivElement> {
	criteria: Criteria;
	forwardedRef?: React.Ref<any>;
	timeZoneId: string;
}

const CONJUNCTION_MAP = {
	[ConjunctionKey.And]: Liferay.Language.get('and'),
	[ConjunctionKey.Or]: Liferay.Language.get('or')
};

class CriteriaView extends React.Component<ICriteriaViewProps> {
	static contextType = ReferencedObjectsContext;

	renderCriteriaGroup(criteria) {
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
							? this.renderCriteriaGroup(criterion)
							: this.renderCriteriaRow(criterion)}
					</Fragment>
				))}
			</div>
		);
	}

	renderCriteriaRow(criterion) {
		const {
			context: {referencedProperties},
			props: {timeZoneId}
		} = this;

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
						timeZoneId={timeZoneId}
					/>
				) : (
					<b className='undefined-property'>
						{Liferay.Language.get('attribute-no-longer-exists')}
					</b>
				)}
			</div>
		);
	}

	render() {
		const {criteria, forwardedRef} = this.props;

		return (
			<div className='criteria-view-root' ref={forwardedRef}>
				{this.renderCriteriaGroup(criteria)}
			</div>
		);
	}
}

export default React.forwardRef<HTMLDivElement, ICriteriaViewProps>(
	(props, ref) => <CriteriaView forwardedRef={ref} {...props} />
);
