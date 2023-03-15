import CardWithRangeKey from '../CardWithRangeKey';
import React from 'react';
import {render} from '@testing-library/react';

jest.mock('shared/hoc/DropdownRangeKey', () => 'DropdownRangeKey');
jest.unmock('react-dom');

const WrappedComponent = () => (
	<CardWithRangeKey>{() => <div>{'foo'}</div>}</CardWithRangeKey>
);

describe('CardWithRangeKey', () => {
	it('render', () => {
		const {container} = render(<WrappedComponent rangeKey='30' />);

		expect(container).toMatchSnapshot();
	});
});
