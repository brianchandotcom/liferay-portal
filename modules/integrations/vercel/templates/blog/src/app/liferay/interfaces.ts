export interface Page<TItems = never> {
  items: TItems[];
  totalCount: number;
  pageSize: number;
  page: number;
  lastPage: number;
}
