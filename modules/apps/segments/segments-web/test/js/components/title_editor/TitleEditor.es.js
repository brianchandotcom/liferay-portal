import React from 'react';
import TitleEditor from 'components/title_editor/TitleEditor.es';
import {cleanup, fireEvent, render} from 'react-testing-library';
import {toMatchDiffSnapshot} from 'snapshot-diff';

expect.extend({toMatchDiffSnapshot});

describe(
	'TitleEditor',
	() => {
		afterEach(cleanup);

		it(
			'should render',
			() => {
				const {asFragment} = render(
					<TitleEditor onChange={jest.fn()} />
				);

				expect(asFragment()).toMatchSnapshot();
			}
		);

		it(
			'should show the text input after clicking on the edit button',
			() => {
				const {asFragment, getByTestId} = render(
					<TitleEditor onChange={jest.fn()} />
				);

				const firstRender = asFragment();

				fireEvent.click(getByTestId('edit-button'));

				expect(firstRender).toMatchDiffSnapshot(asFragment());
			}
		);

		it(
			'should show and hide the text input after blurring from the input',
			() => {
				const {asFragment, getByTestId} = render(
					<TitleEditor onChange={jest.fn()} />
				);

				fireEvent.click(getByTestId('edit-button'));

				const afterClickingRender = asFragment();

				fireEvent.blur(getByTestId('title-input'));

				expect(afterClickingRender).toMatchDiffSnapshot(asFragment());
			}
		);
	}
);