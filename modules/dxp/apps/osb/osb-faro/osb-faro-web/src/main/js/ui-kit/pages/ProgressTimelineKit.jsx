import ProgressTimeline from 'shared/components/ProgressTimeline';
import React from 'react';
import Row from '../components/Row';

class ProgressTimelineKit extends React.Component {
	render() {
		return (
			<div>
				<Row>
					<ProgressTimeline
						activeIndex={1}
						items={[
							{
								title:
									'This is a really long title for this step'
							},
							{title: 'Step 2'},
							{title: 'Step 3'},
							{title: 'Step 4'}
						]}
					/>
				</Row>
			</div>
		);
	}
}

export default ProgressTimelineKit;
