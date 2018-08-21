import {getNumberOfWords} from '../../src/utils/assets';
import {expect} from 'chai';

describe('getNumberOfWords', () => {
	const element = document.createElement('div');

	it('1 should return the total of words', () => {
		element.innerText = 'The standard Lorem Ipsum passage, used since the 1500s';
		const numberOfWords = getNumberOfWords(element);

		expect(numberOfWords).to.equal(9);
	});

	it('2 should return the total of words', () => {
		element.innerText = '1914 translation by H. Rackham';
		const numberOfWords = getNumberOfWords(element);

		expect(numberOfWords).to.equal(5);
	});

	it('3 should return the total of words', () => {
		element.innerText = `On the other hand, we denounce with righteous
			indignation and dislike men who are so beguiled
			and demoralized by the charms of pleasure of the moment`;
		const numberOfWords = getNumberOfWords(element);

		expect(numberOfWords).to.equal(26);
	});

	it('3 should return the total of words', () => {
		element.innerText = '';
		const numberOfWords = getNumberOfWords(element);

		expect(numberOfWords).to.equal(0);
	});
});