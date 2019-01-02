import React from 'react';
import ClaySpinner from 'components/shared/ClaySpinner.es';
import {cleanup, render} from 'react-testing-library';

const SPINNER_TESTID = 'spinner';

describe(
	'ClaySpinner',
	() => {
		afterEach(cleanup);

		it(
			'should render',
			() => {
				const {container} = render(<ClaySpinner loading />);

				expect(container.firstChild).toMatchSnapshot();
			}
		);

		it(
			'should render a small spinner',
			() => {
				const {getByTestId} = render(<ClaySpinner loading size="sm" />);

				const spinnerElement = getByTestId(SPINNER_TESTID);

				const classes = spinnerElement.classList;

				expect(classes.contains('loading-animation-sm')).toBeTruthy();
			}
		);

		it(
			'should not render if the spinner is not loading',
			() => {
				const {queryByTestId} = render(
					<ClaySpinner loading={false} />
				);

				const spinnerElement = queryByTestId(SPINNER_TESTID);

				expect(spinnerElement).toBeNull();
			}
		);
	}
);