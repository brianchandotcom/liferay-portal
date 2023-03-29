import CreateMappingModal from '../CreateMappingModal';
import React from 'react';
import {noop} from 'lodash';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('CreateMappingModal', () => {
	it('should render', () => {
		const {container} = render(
			<CreateMappingModal groupId='23' onClose={noop} />
		);

		expect(container).toMatchSnapshot();
	});
});
