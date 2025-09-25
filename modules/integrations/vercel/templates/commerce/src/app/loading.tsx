import { Skeleton } from "@/components/ui/skeleton";

export default function Loading() {
  return (
    <div>
      <div className="container mx-auto px-4">
        <div className="flex gap-6">
          {/* Aside Filters */}
          <aside>
            <div className="w-80 space-y-6">
              {/* Material Filter */}
              <div className="bg-white rounded-lg p-4 space-y-3">
                <Skeleton className="h-6 w-32" /> {/* Filter title */}
                <div className="space-y-2">
                  {[...Array(6)].map((_, i) => (
                    <div key={i} className="flex items-center gap-2">
                      <Skeleton className="h-4 w-4 rounded" /> {/* checkbox */}
                      <Skeleton className="h-4 w-24" /> {/* label */}
                    </div>
                  ))}
                </div>
              </div>

              {/* Package Quantity Filter */}
              <div className="bg-white rounded-lg p-4 space-y-3">
                <Skeleton className="h-6 w-40" />
                <div className="space-y-2">
                  {[...Array(5)].map((_, i) => (
                    <div key={i} className="flex items-center gap-2">
                      <Skeleton className="h-4 w-4 rounded" />
                      <Skeleton className="h-4 w-20" />
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </aside>

          {/* Product Grid */}
          <div className="flex-1">
            <div className="mb-4 flex items-center justify-between">
              <Skeleton className="h-6 w-40" /> {/* "49 Products Available" */}
              <Skeleton className="h-8 w-32" /> {/* sort dropdown */}
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
              {[...Array(6)].map((_, i) => (
                <div
                  key={i}
                  className="rounded-lg p-4 space-y-3 shadow-sm bg-white"
                >
                  <Skeleton className="h-32 w-full rounded-md" />{" "}
                  {/* product image */}
                  <Skeleton className="h-4 w-28" /> {/* SKU */}
                  <Skeleton className="h-5 w-40" /> {/* product name */}
                  <Skeleton className="h-6 w-20" /> {/* price */}
                  <div className="flex items-center gap-2">
                    <Skeleton className="h-8 w-16" /> {/* qty input */}
                    <Skeleton className="h-8 flex-1" />{" "}
                    {/* add to cart button */}
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
