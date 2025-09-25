export type LocalizedField<T extends string> = {
  [K in T]: string;
} & {
  [K in `${T}_i18n`]: {
    [lang: string]: string;
  };
};
