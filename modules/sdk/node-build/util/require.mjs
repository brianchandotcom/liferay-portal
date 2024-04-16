import module from 'module';
import resolve from 'resolve';

const require = module.createRequire(import.meta.url);

export default function(filePath) {
	return require(resolve.sync(filePath, {basedir: '.'}));
}
