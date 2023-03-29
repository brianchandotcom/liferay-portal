import React from 'react';
import {render} from '@testing-library/react';
import {withProjects} from '../WithProjects';

jest.unmock('react-dom');

describe('WithProjects', () => {
	it('should render WrappedComponent', () => {
		const WrappedComponent = withProjects(() => <div>{'foo'}</div>);

		const {container} = render(<WrappedComponent projects={[{}]} />);

		expect(container).toMatchSnapshot();
	});

	it('should pass projects to the WrappedComponent', () => {
		const WrappedComponent = withProjects(({projects}) => (
			<div>
				{projects && projects.length
					? 'There are projects'
					: 'No projects here'}
			</div>
		));

		const {getByText} = render(<WrappedComponent projects={[{}]} />);

		expect(getByText('There are projects'));
	});
});
