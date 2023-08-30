import React, { useCallback, useEffect, useState } from 'react';
import ClayForm, {ClayInput} from '@clayui/form';

import { getBlogs } from './Blog/getBlogs';
import { addBlog } from './Blog/AddBlog';
import { deleteBlog } from './Blog/deleteBlog';
import { getKnowledgeBase } from './Knowledge_Base/getKnowledgeBase';
import { addKnowledgeBase } from './Knowledge_Base/addKnowledgeBase';
import { deleteKnowledgeBase } from './Knowledge_Base/deleteKnowledgeBase';
import { getDocs } from './Document/getDocuments';
import { addDocument } from './Document/AddDocument';
import { deleteDocument } from './Document/deleteDocument';


function CRUD() {
    //----------------- Blog -----------------//
    //get blogs
    const [blogs, setBlogs] = useState([]);

    useEffect(() => {
        getBlogs().then(res => {
            setBlogs(res.items);
        })
    }, []);

    
    //add a blog
    const [headline, setHeadline] = useState('');
	const [description, setDescription] = useState('');

	const onButtonSubmit_addBlog = useCallback(() => {
		addBlog({
			headline,
            description
		}).then(() => {
			setHeadline('');
			setDescription('');
		});
	},
	[
		addBlog,
		headline,
		description
	]);


    //delete a blog
    const [blogId, setBlogId] = useState('');
    const onButtonSubmit_deleteBlog = useCallback(() => {
		deleteBlog({
            blogId
		}).then(() => {
			setBlogId('');
		});
	},
	[
		deleteBlog,
		blogId
	]);

    //----------------- Knowledge Base -----------------//
    //get knowledge base
    const [knowledge_bases, setKnowledgeBase] = useState([]);

    useEffect(() => {
        getKnowledgeBase().then(res => {
            setKnowledgeBase(res.items);
        })
    }, []);


    //add knowledge base article
    const [title, setTitle] = useState('');
	const [articleBody, setArticleBody] = useState('');

	const onButtonSubmit_addKB = useCallback(() => {
		addKnowledgeBase({
			title,
            articleBody
		}).then(() => {
			setTitle('');
			setArticleBody('');
		});
	},
	[
		addKnowledgeBase,
		title,
		articleBody
	]);


    //delete knoweledge base article
    const [articleId, setArticleId] = useState('');
    const onButtonSubmit_deleteKB = useCallback(() => {
		deleteKnowledgeBase({
            articleId
		}).then(() => {
			setArticleId('');
		});
	},
	[
		deleteKnowledgeBase,
		articleId
	]);


    //----------------- Documents and Media -----------------//
    //get document
    const [documents, setDocument] = useState([]);

    useEffect(() => {
        getDocs().then(res => {
            setDocument(res.items);
        })
    }, []);


    //add document
    const [title_doc, setTitle_Doc] = useState('');
    const [file, setFile] = useState(null);
	const [description_doc, setDescription_Doc] = useState('');

	const onButtonSubmit_addDoc = useCallback(() => {
		addDocument({
			title_doc,
            description_doc,
            file
		}).then(() => {
			setTitle_Doc('');
			setDescription_Doc('');
		});
	},
	[
		addDocument,
		title_doc,
		description_doc,
        file
	]);

    //delete knoweledge base article
    const [documentId, setDocumentId] = useState('');
    const onButtonSubmit_deleteDoc = useCallback(() => {
		deleteDocument({
            documentId
		}).then(() => {
			setDocumentId('');
		});
	},
	[
		deleteDocument,
		documentId
	]);


    //-----------------------------------------------------------------------------
	return (
		<div className="container">
            <div className="row">
                <div className="col-2">
                    <h1>Blogs:</h1>

                    {blogs.map(blog => (
                        <div id={blog.id} key={blog.id}>
                            <h1>{blog.headline}</h1>
                            <br/>
                            <h1>{blog.description}</h1>
                        </div>
                    ))}
                </div>
            </div>
            <br/>
            <div className="row">
                <div className="col-2">
                    <ClayForm.Group>
                        <label htmlFor="headline">Headline</label>

                        <ClayInput
                            id="headline"
                            onChange={event => setHeadline(event.target.value)}
                            placeholder="Headline"
                            type="text"
                            value={headline}
                        />
                    </ClayForm.Group>
                    <ClayForm.Group>
                        <label htmlFor="description">Description</label>

                        <ClayInput
                            id="description"
                            onChange={event => setDescription(event.target.value)}
                            placeholder="description"
                            type="text"
                            value={description}
                        />
                    </ClayForm.Group>

                    <button className="btn btn-primary" onClick={() => onButtonSubmit_addBlog()}>Add Blog</button>
                </div>
                <div className="col-2">
                    <ClayForm.Group>
                        <label htmlFor="blogId">Blog ID</label>

                        <ClayInput
                            id="blogId"
                            onChange={event => setBlogId(event.target.value)}
                            placeholder="blogId"
                            type="text"
                            value={blogId}
                        />
                    </ClayForm.Group>

                    <button className="btn btn-primary" onClick={() => onButtonSubmit_deleteBlog()}>Delete Blog</button>
                </div>
            </div>
            <br/>
            <div className="row">
                <div className="col-2">
                    <h1>Knowledge Base:</h1>

                    {knowledge_bases.map(knowledge_base => (
                        <div id={knowledge_base.id} key={knowledge_base.id}>
                            <h1>{knowledge_base.title}</h1>
                            <br/>
                            <h1>{knowledge_base.articleBody}</h1>
                        </div>
                    ))}
                </div>
            </div>
            <br/>
            <div className="row">
                <div className="col-2">
                    <ClayForm.Group>
                        <label htmlFor="title">Article Title</label>

                        <ClayInput
                            id="title"
                            onChange={event => setTitle(event.target.value)}
                            placeholder="title"
                            type="text"
                            value={title}
                        />
                    </ClayForm.Group>
                    <ClayForm.Group>
                        <label htmlFor="articleBody">Content</label>

                        <ClayInput
                            id="articleBody"
                            onChange={event => setArticleBody(event.target.value)}
                            placeholder="articleBody"
                            type="text"
                            value={articleBody}
                        />
                    </ClayForm.Group>

                    <button className="btn btn-primary" onClick={() => onButtonSubmit_addKB()}>Add Knowledge Base Article</button>
                </div>
                <div className="col-2">
                    <ClayForm.Group>
                        <label htmlFor="articleId">Article ID</label>

                        <ClayInput
                            id="articleId"
                            onChange={event => setArticleId(event.target.value)}
                            placeholder="articleId"
                            type="text"
                            value={articleId}
                        />
                    </ClayForm.Group>

                    <button className="btn btn-primary" onClick={() => onButtonSubmit_deleteKB()}>Delete Blog</button>
                </div>
            </div>
            <br/>
            <div className="row">
                <div className="col-2">
                    <h1>Documents:</h1>

                    {documents.map(doc => (
                        <div id={doc.id} key={doc.id}>
                            <h1>{doc.title}</h1>
                            <br/>
                            <h1>{doc.description}</h1>
                        </div>
                    ))}
                </div>
            </div>
            <br/>
            <div className="row">
                <div className="col-5">
                    <ClayForm.Group>
                        <label htmlFor="title_doc">Document Title</label>

                        <ClayInput
                            id="title_doc"
                            onChange={event => setTitle_Doc(event.target.value)}
                            placeholder="Title"
                            type="text"
                            value={title_doc}
                        />
                    </ClayForm.Group>
                    <ClayForm.Group>
                        <label htmlFor="description_doc">Content</label>

                        <ClayInput
                            id="description_doc"
                            onChange={event => setDescription_Doc(event.target.value)}
                            placeholder="Description"
                            type="text"
                            value={description_doc}
                        />
                    </ClayForm.Group>
                    <ClayForm.Group>
                        <label htmlFor="file">File</label>

                        <ClayInput
                            id="file"
                            onChange={event => setFile(event.target.files[0])}
                            placeholder="file"
                            type="file"
                        />
                    </ClayForm.Group>
                    <button className="btn btn-primary" onClick={() => onButtonSubmit_addDoc()}>Add Document</button>
                </div>
                <div className="col-2">
                    <ClayForm.Group>
                        <label htmlFor="documentId">Document ID</label>

                        <ClayInput
                            id="documentId"
                            onChange={event => setDocumentId(event.target.value)}
                            placeholder="documentId"
                            type="text"
                            value={documentId}
                        />
                    </ClayForm.Group>

                    <button className="btn btn-primary" onClick={() => onButtonSubmit_deleteDoc()}>Delete Document</button>
                </div>
            </div>
		</div>
	);
}

export default CRUD;