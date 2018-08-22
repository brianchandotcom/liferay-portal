import {getNumberOfWords} from '../../src/utils/assets';
import {expect} from 'chai';

describe('getNumberOfWords', () => {
	it('should return the number of words', () => {
		const content = {
			description: 'Build portals, intranets, websites and connected experiences on the most flexible platform around.',
			title: 'Digital Experience Software Tailored to Your Needs',
		};

		const markup = `<header class="header">
							<h2>${content.title}</h2>
							<p>${content.description}</p>
						</header>`;

		const element = document.createElement('div');
		element.innerHTML = markup;

		const numberOfWords = getNumberOfWords(element);

		expect(numberOfWords).to.equal(20);
	});

	it('should return 0 if the number of words is empty', () => {
		const element = document.createElement('div');

		element.innerText = '';
		const numberOfWords = getNumberOfWords(element);

		expect(numberOfWords).to.equal(0);
	});
});