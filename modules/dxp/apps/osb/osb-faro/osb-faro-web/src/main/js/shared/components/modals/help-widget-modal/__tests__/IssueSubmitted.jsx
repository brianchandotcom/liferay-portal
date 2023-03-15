import IssueSubmitted from '../IssueSubmitted';
import React from 'react';
import {noop} from 'lodash';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('IssueSubmitted', () => {
	it('should render', () => {
		const {container} = render(
			<IssueSubmitted onClose={noop} onNext={noop} />
		);

		expect(container).toMatchSnapshot();
	});
});
