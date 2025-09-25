import { Button } from "./button";

export const Pagination = ({
  currentPage,
  lastPage,
}: {
  currentPage: number;
  lastPage: number;
}) => {
  const pages = Array.from(Array(lastPage).keys()).map((x) => x + 1);

  return (
    <nav className="flex gap-2 justify-center">
      {pages.map((pageNumber) => (
        <Button
          key={pageNumber}
          href={`?page=${pageNumber}`}
          active={currentPage === pageNumber}
        >
          {pageNumber}
        </Button>
      ))}
    </nav>
  );
};
