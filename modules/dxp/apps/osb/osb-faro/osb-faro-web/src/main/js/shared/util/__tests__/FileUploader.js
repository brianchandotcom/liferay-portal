/* eslint-disable no-underscore-dangle */

import FileUploader, {ERROR_TYPES, normalizeFiles} from '../FileUploader';

describe('FileUploader', () => {
	let uploader;

	afterEach(() => {
		if (uploader) {
			uploader.destroy();
		}
	});

	function mockFile(name, type = 'foo') {
		return new File([''], name, {type});
	}

	function mockEvent(...names) {
		const event = new Event('change');

		Object.defineProperty(event, 'target', {
			value: {
				files: names.map(mockFile)
			}
		});

		return event;
	}

	it('renders', () => {
		uploader = new FileUploader().render();

		expect(uploader).toBeTruthy();

		expect(document.body.innerHTML).toContain('<input');
		expect(document.body.innerHTML).toContain('type="file"');
		expect(document.body.innerHTML).toContain('hidden');
	});

	it('should remove input', () => {
		uploader = new FileUploader().render();

		uploader.destroy();

		expect(document.body.innerHTML).toBe('');
	});

	it('should set multiple if maxFileCount is more than one', () => {
		uploader = new FileUploader({maxFileCount: 2}).render();

		expect(uploader._inputNode.multiple).toBe(true);
	});

	it('should not set multiple if maxFileCount is one', () => {
		uploader = new FileUploader({maxFileCount: 1}).render();

		expect(uploader._inputNode.multiple).toBe(false);
	});

	it('should set constants from config', () => {
		const CONSTANTS = {
			onChange: jest.fn(),
			onError: jest.fn(),
			uploadURL: 'test/url/path'
		};

		uploader = new FileUploader(CONSTANTS).render();

		expect(uploader.onChange).toBe(CONSTANTS.onChange);
		expect(uploader.onError).toBe(CONSTANTS.onError);
		expect(uploader.uploadURL).toBe(CONSTANTS.uploadURL);
	});

	it('should trigger file selector', () => {
		const spy = jest.fn();

		uploader = new FileUploader().render();

		uploader._inputNode.click = spy;

		uploader.openDialog();

		expect(spy).toBeCalled();
	});

	it('should not call onError', () => {
		const file = mockFile('test.jpg');

		const spy = jest.fn();

		uploader = new FileUploader({
			onError: spy
		}).render();

		uploader.upload([file]);

		expect(spy).not.toBeCalled();
	});

	it('should call `onChange`', () => {
		uploader = new FileUploader({
			onChange: jest.fn()
		}).render();

		uploader.onLoad('foo', 1, mockEvent('foo'));

		expect(uploader.onChange).toHaveBeenCalled();
	});

	it('should call `onChange`', () => {
		uploader = new FileUploader({
			onChange: jest.fn()
		}).render();

		uploader.onStart('foo', 1, 'bar');

		expect(uploader.onChange).toHaveBeenCalled();
	});

	it('should clear the FileUploaders value on openDialog', () => {
		const uploaderValue = 'tests';

		uploader = new FileUploader().render();

		Object.defineProperty(uploader._inputNode, 'value', {
			value: uploaderValue,
			writable: true
		});

		expect(uploader._inputNode.value).toBe(uploaderValue);

		uploader.openDialog();

		expect(uploader._inputNode.value).toBe('');
	});

	describe('onProgress', () => {
		it('should call onChange', () => {
			const spy = jest.fn();

			uploader = new FileUploader({
				onChange: spy
			}).render();

			uploader.onProgress('', 1, {target: {}});

			expect(spy).toBeCalled();
		});
	});

	describe('addFiles', () => {
		const {FILE_LIMIT, INVALID_FILE, MULTIPLE_FILES} = ERROR_TYPES;

		it.each`
			props                            | files                    | error
			${{}}                            | ${['img.png']}           | ${INVALID_FILE}
			${{}}                            | ${['somecsv']}           | ${INVALID_FILE}
			${{}}                            | ${['a.csv', 'b.csv']}    | ${MULTIPLE_FILES}
			${{maxFileCount: 2}}             | ${['a.c', 'b.c', 'd.c']} | ${FILE_LIMIT}
			${{maxFileCount: 0}}             | ${['a.csv']}             | ${FILE_LIMIT}
			${{maxFileCount: -1}}            | ${['a.csv']}             | ${FILE_LIMIT}
			${{}}                            | ${['thing.csv']}         | ${null}
			${{fileTypes: ['.png']}}         | ${['img.png']}           | ${null}
			${{fileTypes: ['.png', '.csv']}} | ${['img.png']}           | ${null}
		`(
			'should have $error error with $props and files $files',
			({error, files, props}) => {
				const onError = jest.fn();

				uploader = new FileUploader({
					fileTypes: ['.csv'],
					onError,
					...props
				}).render();

				uploader.addFiles(files.map(mockFile));

				if (error) {
					expect(onError).toHaveBeenCalledWith(error);
				} else {
					expect(onError).not.toHaveBeenCalled();
				}
			}
		);
	});

	describe('normalizeFiles', () => {
		it.each`
			files                          | results
			${mockFile('foo.csv')}         | ${['foo.csv']}
			${[mockFile('foo.csv')]}       | ${['foo.csv']}
			${mockEvent('foo.csv')}        | ${['foo.csv']}
			${mockEvent('a.csv', 'b.csv')} | ${['a.csv', 'b.cv']}
		`('should normalize $file to $results', ({files, results}) => {
			expect(normalizeFiles(files)).toEqual(results.map(mockFile));
		});
	});
});
