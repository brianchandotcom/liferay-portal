import { FieldsLoader, getFieldsFromModules } from '../FieldsLoader.es';

const modules = {
    "ddm-poc-field-type-date@1.0.0/lib/index.es": {
        "dependencies": ["module", "require", "exports", "ddm-poc-field-type-base"],
        "map": {
            "ddm-poc-field-type-base": "ddm-poc-field-type-base@1.0.0",
        }
    },
    "ddm-poc-field-type-grid@1.0.0/lib/index.es": {
        "dependencies": ["module", "require", "exports", "ddm-poc-field-type-base"],
        "map": {
            "ddm-poc-field-type-base": "ddm-poc-field-type-base@1.0.0",
        }
    },
    "ddm-poc-field-type-options@1.0.0/lib/index.es": {
        "dependencies": ["module", "require", "exports", "ddm-poc-field-type-base"],
        "map": {
            "ddm-poc-field-type-base": "ddm-poc-field-type-base@1.0.0",
        }
    },
    "ddm-poc-field-type-text@1.0.0/lib/index.es": {
        "dependencies": ["module", "require", "exports"],
        "map": {}
    }
};

describe('FieldsLoader', () => {
    beforeEach(() => {
        window.Liferay = {
            Loader: {
                require: jest.fn()
            }
        }
    });

    afterEach(() => {
        window.Liferay.Loader.require.mockClear();
    });

    it('should list the packages that depend on the list of past dependencies', () => {
        const dependenciesToCompare = ['ddm-poc-field-type-base'];

        expect(
            getFieldsFromModules(modules, dependenciesToCompare)
        ).toEqual([
            "ddm-poc-field-type-date@1.0.0/lib/index.es",
            "ddm-poc-field-type-grid@1.0.0/lib/index.es",
            "ddm-poc-field-type-options@1.0.0/lib/index.es"
        ]);
    });

    it('should list the packages that depend on the list of past dependencies and ignore themselves', () => {
        const dependenciesToCompare = ['ddm-poc-field-type-base'];
        const modulesWithDependenciesCompare = 
            Object.assign({}, modules, {
                "ddm-poc-field-type-base@1.0.0/lib/index.es": {
                    "dependencies": ["module", "require", "exports"],
                    "map": {}
                }
            });

        expect(
            getFieldsFromModules(modulesWithDependenciesCompare, dependenciesToCompare)
        ).toEqual([
            "ddm-poc-field-type-date@1.0.0/lib/index.es",
            "ddm-poc-field-type-grid@1.0.0/lib/index.es",
            "ddm-poc-field-type-options@1.0.0/lib/index.es"
        ]);
    });

    it('should return an empty list when dependencies to compare are not passed', () => {
        expect(
            getFieldsFromModules(modules)
        ).toEqual([]);
    });

    it('should load fields when there are packages with dependencies from the list of past dependencies', () => {
        const dependenciesToCompare = ['ddm-poc-field-type-base'];
        const result = FieldsLoader(jest.fn(), modules, dependenciesToCompare);

        expect(Liferay.Loader.require).toHaveBeenCalled();
        expect(result).toBe(true);
    });

    it('should not load fields when the dependencies to compare are empty and only call the callback', () => {
        const dependenciesToCompare = [];

        // Stub callback
        const callback = jest.fn();

        expect(
            FieldsLoader(callback, modules, dependenciesToCompare)
        ).toBe(false);
        expect(callback).toHaveBeenCalled();
    });

    it('should not load fields when the dependencies to compare is not passed and only call the callback', () => {
        // Stub callback
        const callback = jest.fn();

        expect(
            FieldsLoader(callback, modules)
        ).toBe(false);
        expect(callback).toHaveBeenCalled();
    });
});