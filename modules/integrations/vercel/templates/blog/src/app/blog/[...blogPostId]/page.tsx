import { Metadata } from "next";
import Image from "next/image";
import { PropsWithChildren } from "react";

import { liferay } from "@/app/liferay/server";

import { getCMSBlogPosting } from "./data";
import { Button } from "@/app/components/button";

interface PageProps {
  params: Promise<{ blogPostId: [string, string] }>;
}

const PageTemplate = ({ children }: PropsWithChildren) => {
  return (
    <div className="container mx-auto sm:max-w-4xl">
      <Button href="/">&lt;&lt; Back</Button>

      {children}
    </div>
  );
};

export async function generateMetadata(
  pageProps: PageProps
): Promise<Metadata> {
  const {
    blogPostId: [blogId],
  } = await pageProps.params;

  const { data } = await getCMSBlogPosting({
    liferay,
    blogId: Number(blogId),
  });

  return {
    title: data?.title,
    description: data?.subtitle,
  };
}

export default async function BlogPost(pageProps: PageProps) {
  const {
    blogPostId: [blogId],
  } = await pageProps.params;

  const { data, error } = await getCMSBlogPosting({
    liferay,
    blogId: Number(blogId),
  });

  if (error || !data) {
    return (
      <PageTemplate>
        <pre className="font-mono">{JSON.stringify(error, null, 2)}</pre>
      </PageTemplate>
    );
  }

  const src = liferay.getDocument(data.coverImage.link.href);

  return (
    <PageTemplate>
      <article>
        <header className="my-4 flex flex-col gap-8">
          <div className="card">
            <Image
              alt={data.coverImage.link.label}
              className="w-full aspect-video object-cover"
              height={90}
              priority={true}
              src={src}
              unoptimized={true}
              width={160}
            />
          </div>

          <div>
            <h1 className="text-3xl sm:text-4xl font-bold">{data.title}</h1>
            <p className="mt-4">{data.subtitle}</p>
          </div>

          <footer className="text-sm/6">
            <section className="flex gap-2">
              <span>
                By <strong>{data.creator.name}</strong>
              </span>
              <span>-</span>
              <span>{new Date(data.dateCreated).toLocaleDateString()}</span>
            </section>
          </footer>
        </header>

        <section>
          <div
            className="flex gap-4 flex-col text-justify"
            dangerouslySetInnerHTML={{ __html: data.content }}
          />

          <footer className="my-12">
            <div className="flex gap-2">
              <span>Tags:</span>
              {data.keywords.map((tag) => (
                <strong key={tag}>{tag}</strong>
              ))}
            </div>
          </footer>
        </section>
      </article>
    </PageTemplate>
  );
}
