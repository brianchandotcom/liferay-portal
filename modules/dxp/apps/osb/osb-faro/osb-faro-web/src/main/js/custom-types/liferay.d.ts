declare namespace Liferay {
	const FeatureFlags: Record<string, boolean>;

	namespace Language {
		function get(languageKey: string);
	}

	namespace Util {
		function fetch(url: string, options?: any): Promise<any>;
	}
}
