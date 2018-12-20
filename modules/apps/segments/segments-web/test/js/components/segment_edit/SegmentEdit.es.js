import React from 'react';
import SegmentEdit from 'components/segment_edit/SegmentEdit.es';
import {cleanup, render} from 'react-testing-library';

describe(
	'SegmentEdit',
	() => {
		afterEach(cleanup);

		it(
			'should render',
			() => {
				const {asFragment} = render(
					<SegmentEdit locale="en_US" />
				);

				expect(asFragment()).toMatchSnapshot();
			}
		);
	}
);