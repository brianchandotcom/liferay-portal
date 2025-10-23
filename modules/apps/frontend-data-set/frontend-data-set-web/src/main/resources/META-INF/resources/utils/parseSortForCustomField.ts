const CUSTOM_FIELD_KEY = "customField";
const CUSTOM_FIELD_DEFAULT_DENOMINATOR = ".";
const CUSTOM_FIELD_NEW_DENOMINATOR = "/";

export function parseSortForCustomField(key: string) {
    return key.startsWith(CUSTOM_FIELD_KEY) ? key.replace(CUSTOM_FIELD_DEFAULT_DENOMINATOR, CUSTOM_FIELD_NEW_DENOMINATOR) : key;
}