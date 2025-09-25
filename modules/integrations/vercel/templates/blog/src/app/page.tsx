import { PropsWithChildren } from "react";
import Image from "next/image";

import { liferay } from "@/app/liferay/server";

import { getCMSBlogPostings } from "./data";
import { Button } from "./components/button";
import { Pagination } from "./components/pagination";

const PageTemplate = ({ children }: PropsWithChildren) => {
  return <div className="container mx-auto sm:max-w-4xl">{children}</div>;
};

export default async function Home({
  searchParams,
}: {
  searchParams: Promise<{ page: string | undefined }>;
}) {
  const params = await searchParams;
  const page = Number(params.page || 1);

  const { data, error } = await getCMSBlogPostings({ liferay, page });

  if (error || !data) {
    return (
      <PageTemplate>
        <details className="rounded-md p-4 border">
          <summary>Error: not able to load blog posts</summary>

          <pre className="font-mono">{JSON.stringify(error, null, 2)}</pre>
        </details>
      </PageTemplate>
    );
  }

  return (
    <PageTemplate>
      <ol className="text-sm/6 text-left grid grid-rows-1 sm:grid-cols-2 gap-4 mb-4">
        {data.items.map((blog, index) => {
          const src = liferay.getDocument(blog.coverImage.link.href);

          return (
            <li key={blog.id} className="tracking-[-.01em] first:sm:col-span-2">
              <article className="card">
                <div className="border-b-1 border-blue-200">
                  <Image
                    alt={blog.coverImage.link.label}
                    className="w-full aspect-video object-cover"
                    height={90}
                    priority={index < 5}
                    src={src}
                    unoptimized={true}
                    width={160}
                  />
                </div>

                <div className="flex flex-col gap-4 p-5">
                  <h2 className="text-xl font-bold">{blog.title}</h2>

                  <p>
                    {blog.contentRawText.split(" ").slice(0, 30).join(" ")}...
                  </p>

                  <div className="flex gap-2">
                    <span>
                      By <strong>{blog.creator.name}</strong>
                    </span>
                    <span>-</span>
                    <span>
                      {new Date(blog.dateCreated).toLocaleDateString()}
                    </span>
                  </div>

                  <Button href={`/blog/${blog.id}/${blog.friendlyUrlPath}`}>
                    read more
                  </Button>
                </div>
              </article>
            </li>
          );
        })}
      </ol>

      <Pagination lastPage={data.lastPage} currentPage={page} />
    </PageTemplate>
  );
}
