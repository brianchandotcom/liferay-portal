import autobind from 'autobind-decorator';
import BaseResults from 'shared/components/BaseResults';
import React from 'react';
import VerticalTimeline from 'shared/components/VerticalTimelineDeprecated';
import {PropTypes} from 'prop-types';
import {withStatefulPagination} from 'shared/hoc';

export default class SearchableVerticalTimeline extends React.Component {
	static defaultProps = {
		initialExpanded: true,
		items: [],
		loading: false,
		nested: false
	};

	static propTypes = {
		className: PropTypes.string,
		groupId: PropTypes.string,
		headerLabels: PropTypes.object,
		initialExpanded: PropTypes.bool,
		items: PropTypes.arrayOf(PropTypes.object),
		loading: PropTypes.bool,
		nested: PropTypes.bool,
		timeZoneId: PropTypes.string
	};

	@autobind
	renderVerticalTimeline({items, loading}) {
		const {
			groupId,
			headerLabels,
			initialExpanded,
			nested,
			timeZoneId
		} = this.props;

		return (
			<VerticalTimeline
				groupId={groupId}
				headerLabels={headerLabels}
				initialExpanded={initialExpanded}
				items={items}
				loading={loading}
				nested={nested}
				timeZoneId={timeZoneId}
			/>
		);
	}

	render() {
		const {className} = this.props;

		return (
			<BaseResults
				{...this.props}
				className={className}
				resultsRenderer={this.renderVerticalTimeline}
			/>
		);
	}
}

SearchableVerticalTimeline.StatefulPagination = withStatefulPagination(
	SearchableVerticalTimeline
);
