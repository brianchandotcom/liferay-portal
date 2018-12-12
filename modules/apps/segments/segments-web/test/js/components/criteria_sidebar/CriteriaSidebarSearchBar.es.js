import React from 'react';
import CriteriaSidebarSearchBar from 'components/criteria_sidebar/CriteriaSidebarSearchBar.es';
import renderer from 'react-test-renderer';

describe(
	'CriteriaSidebarSearchBar',
	() => {
		it(
			'should render',
			() => {
				const component = renderer.create(
					<CriteriaSidebarSearchBar />
				).toJSON();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render with a close icon with an input',
			() => {
				const component = renderer.create(
					<CriteriaSidebarSearchBar searchValue={'test'} />
				).toJSON();

				expect(component).toMatchSnapshot();
			}
		);
	}
);