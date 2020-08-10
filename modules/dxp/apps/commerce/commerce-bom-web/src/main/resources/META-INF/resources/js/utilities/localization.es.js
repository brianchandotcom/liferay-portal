export function convertString(string) {
    try {
        return window.Liferay.Language.get(string)
    } catch (error) {
        return string
    }
}
