import ShareFormPopover from 'source/components/ShareFormPopover/ShareFormPopover.es';

const spritemap = 'spritemap';
const url = 'publish/url';

const props = {
	spritemap,
	strings: {
		'copied-to-clipboard': 'copied-to-clipboard'
	},
	url
};

let shareFormIcon;

describe(
	'ShareFormPopover',
	() => {
		let component;

		afterEach(
			() => {
				if (component) {
					component.dispose();
				}
			}
		);

		beforeEach(
			() => {
				shareFormIcon = document.createElement('span');
				shareFormIcon.classList.add('share-form-icon');

				document.querySelector('body').appendChild(shareFormIcon);

				jest.useFakeTimers();
				fetch.resetMocks();
			}
		);

		it(
			'should render the default markup',
			() => {
				component = new ShareFormPopover(props);
				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should copy the sharable URL to user\'s clipboard',
			() => {
				component = new ShareFormPopover(props);
				component._clipboard.emit('success');

				jest.runAllTimers();

				expect(component.state.success).toBeTruthy();
				expect(component).toMatchSnapshot();
			}
		);
	}
);