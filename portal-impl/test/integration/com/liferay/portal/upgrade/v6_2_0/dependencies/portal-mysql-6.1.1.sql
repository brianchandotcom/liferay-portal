drop database if exists lportal;
create database lportal character set utf8;
use lportal;

-- MySQL dump 10.13  Distrib 5.5.31, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: lportal_6_1_1_ce
-- ------------------------------------------------------
-- Server version	5.5.31-0ubuntu0.12.04.2-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Account_`
--

DROP TABLE IF EXISTS `Account_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Account_` (
  `accountId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `parentAccountId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `legalName` varchar(75) DEFAULT NULL,
  `legalId` varchar(75) DEFAULT NULL,
  `legalType` varchar(75) DEFAULT NULL,
  `sicCode` varchar(75) DEFAULT NULL,
  `tickerSymbol` varchar(75) DEFAULT NULL,
  `industry` varchar(75) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `size_` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Account_`
--

LOCK TABLES `Account_` WRITE;
/*!40000 ALTER TABLE `Account_` DISABLE KEYS */;
INSERT INTO `Account_` VALUES (10156,10154,0,'','2013-07-10 10:34:14','2013-07-10 10:34:14',0,'liferay.com','','','','','','','','');
/*!40000 ALTER TABLE `Account_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Address`
--

DROP TABLE IF EXISTS `Address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Address` (
  `addressId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `street1` varchar(75) DEFAULT NULL,
  `street2` varchar(75) DEFAULT NULL,
  `street3` varchar(75) DEFAULT NULL,
  `city` varchar(75) DEFAULT NULL,
  `zip` varchar(75) DEFAULT NULL,
  `regionId` bigint(20) DEFAULT NULL,
  `countryId` bigint(20) DEFAULT NULL,
  `typeId` int(11) DEFAULT NULL,
  `mailing` tinyint(4) DEFAULT NULL,
  `primary_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`addressId`),
  KEY `IX_93D5AD4E` (`companyId`),
  KEY `IX_ABD7DAC0` (`companyId`,`classNameId`),
  KEY `IX_71CB1123` (`companyId`,`classNameId`,`classPK`),
  KEY `IX_923BD178` (`companyId`,`classNameId`,`classPK`,`mailing`),
  KEY `IX_9226DBB4` (`companyId`,`classNameId`,`classPK`,`primary_`),
  KEY `IX_5BC8B0D4` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Address`
--

LOCK TABLES `Address` WRITE;
/*!40000 ALTER TABLE `Address` DISABLE KEYS */;
/*!40000 ALTER TABLE `Address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AnnouncementsDelivery`
--

DROP TABLE IF EXISTS `AnnouncementsDelivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AnnouncementsDelivery` (
  `deliveryId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `email` tinyint(4) DEFAULT NULL,
  `sms` tinyint(4) DEFAULT NULL,
  `website` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`deliveryId`),
  UNIQUE KEY `IX_BA4413D5` (`userId`,`type_`),
  KEY `IX_6EDB9600` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AnnouncementsDelivery`
--

LOCK TABLES `AnnouncementsDelivery` WRITE;
/*!40000 ALTER TABLE `AnnouncementsDelivery` DISABLE KEYS */;
/*!40000 ALTER TABLE `AnnouncementsDelivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AnnouncementsEntry`
--

DROP TABLE IF EXISTS `AnnouncementsEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AnnouncementsEntry` (
  `uuid_` varchar(75) DEFAULT NULL,
  `entryId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `title` varchar(75) DEFAULT NULL,
  `content` longtext,
  `url` longtext,
  `type_` varchar(75) DEFAULT NULL,
  `displayDate` datetime DEFAULT NULL,
  `expirationDate` datetime DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `alert` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`entryId`),
  KEY `IX_A6EF0B81` (`classNameId`,`classPK`),
  KEY `IX_14F06A6B` (`classNameId`,`classPK`,`alert`),
  KEY `IX_D49C2E66` (`userId`),
  KEY `IX_1AFBDE08` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AnnouncementsEntry`
--

LOCK TABLES `AnnouncementsEntry` WRITE;
/*!40000 ALTER TABLE `AnnouncementsEntry` DISABLE KEYS */;
/*!40000 ALTER TABLE `AnnouncementsEntry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AnnouncementsFlag`
--

DROP TABLE IF EXISTS `AnnouncementsFlag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AnnouncementsFlag` (
  `flagId` bigint(20) NOT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `entryId` bigint(20) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  PRIMARY KEY (`flagId`),
  UNIQUE KEY `IX_4539A99C` (`userId`,`entryId`,`value`),
  KEY `IX_9C7EB9F` (`entryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AnnouncementsFlag`
--

LOCK TABLES `AnnouncementsFlag` WRITE;
/*!40000 ALTER TABLE `AnnouncementsFlag` DISABLE KEYS */;
/*!40000 ALTER TABLE `AnnouncementsFlag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AssetCategory`
--

DROP TABLE IF EXISTS `AssetCategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AssetCategory` (
  `uuid_` varchar(75) DEFAULT NULL,
  `categoryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `parentCategoryId` bigint(20) DEFAULT NULL,
  `leftCategoryId` bigint(20) DEFAULT NULL,
  `rightCategoryId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `title` longtext,
  `description` longtext,
  `vocabularyId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`categoryId`),
  UNIQUE KEY `IX_BE4DF2BF` (`parentCategoryId`,`name`,`vocabularyId`),
  UNIQUE KEY `IX_E8D019AA` (`uuid_`,`groupId`),
  KEY `IX_E639E2F6` (`groupId`),
  KEY `IX_510B46AC` (`groupId`,`parentCategoryId`,`name`),
  KEY `IX_2008FACB` (`groupId`,`vocabularyId`),
  KEY `IX_D61ABE08` (`name`,`vocabularyId`),
  KEY `IX_7BB1826B` (`parentCategoryId`),
  KEY `IX_9DDD15EA` (`parentCategoryId`,`name`),
  KEY `IX_B185E980` (`parentCategoryId`,`vocabularyId`),
  KEY `IX_4D37BB00` (`uuid_`),
  KEY `IX_287B1F89` (`vocabularyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AssetCategory`
--

LOCK TABLES `AssetCategory` WRITE;
/*!40000 ALTER TABLE `AssetCategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `AssetCategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AssetCategoryProperty`
--

DROP TABLE IF EXISTS `AssetCategoryProperty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AssetCategoryProperty` (
  `categoryPropertyId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `categoryId` bigint(20) DEFAULT NULL,
  `key_` varchar(75) DEFAULT NULL,
  `value` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`categoryPropertyId`),
  UNIQUE KEY `IX_DBD111AA` (`categoryId`,`key_`),
  KEY `IX_99DA856` (`categoryId`),
  KEY `IX_8654719F` (`companyId`),
  KEY `IX_52340033` (`companyId`,`key_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AssetCategoryProperty`
--

LOCK TABLES `AssetCategoryProperty` WRITE;
/*!40000 ALTER TABLE `AssetCategoryProperty` DISABLE KEYS */;
/*!40000 ALTER TABLE `AssetCategoryProperty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AssetEntries_AssetCategories`
--

DROP TABLE IF EXISTS `AssetEntries_AssetCategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AssetEntries_AssetCategories` (
  `entryId` bigint(20) NOT NULL,
  `categoryId` bigint(20) NOT NULL,
  PRIMARY KEY (`entryId`,`categoryId`),
  KEY `IX_A188F560` (`categoryId`),
  KEY `IX_E119938A` (`entryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AssetEntries_AssetCategories`
--

LOCK TABLES `AssetEntries_AssetCategories` WRITE;
/*!40000 ALTER TABLE `AssetEntries_AssetCategories` DISABLE KEYS */;
/*!40000 ALTER TABLE `AssetEntries_AssetCategories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AssetEntries_AssetTags`
--

DROP TABLE IF EXISTS `AssetEntries_AssetTags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AssetEntries_AssetTags` (
  `entryId` bigint(20) NOT NULL,
  `tagId` bigint(20) NOT NULL,
  PRIMARY KEY (`entryId`,`tagId`),
  KEY `IX_2ED82CAD` (`entryId`),
  KEY `IX_B2A61B55` (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AssetEntries_AssetTags`
--

LOCK TABLES `AssetEntries_AssetTags` WRITE;
/*!40000 ALTER TABLE `AssetEntries_AssetTags` DISABLE KEYS */;
/*!40000 ALTER TABLE `AssetEntries_AssetTags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AssetEntry`
--

DROP TABLE IF EXISTS `AssetEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AssetEntry` (
  `entryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `classUuid` varchar(75) DEFAULT NULL,
  `classTypeId` bigint(20) DEFAULT NULL,
  `visible` tinyint(4) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `publishDate` datetime DEFAULT NULL,
  `expirationDate` datetime DEFAULT NULL,
  `mimeType` varchar(75) DEFAULT NULL,
  `title` longtext,
  `description` longtext,
  `summary` longtext,
  `url` longtext,
  `layoutUuid` varchar(75) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `priority` double DEFAULT NULL,
  `viewCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`entryId`),
  UNIQUE KEY `IX_1E9D371D` (`classNameId`,`classPK`),
  KEY `IX_FC1F9C7B` (`classUuid`),
  KEY `IX_7306C60` (`companyId`),
  KEY `IX_75D42FF9` (`expirationDate`),
  KEY `IX_1EBA6821` (`groupId`,`classUuid`),
  KEY `IX_2E4E3885` (`publishDate`),
  KEY `IX_9029E15A` (`visible`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AssetEntry`
--

LOCK TABLES `AssetEntry` WRITE;
/*!40000 ALTER TABLE `AssetEntry` DISABLE KEYS */;
INSERT INTO `AssetEntry` VALUES (10178,10172,10154,10158,'','2013-07-10 10:34:14','2013-07-10 10:34:14',10117,10176,'68fca0a5-5d42-41b5-9777-8e35ee73ec85',0,0,NULL,NULL,NULL,NULL,'text/html','10175','','','','',0,0,0,0),(10186,10180,10154,10158,'','2013-07-10 10:34:14','2013-07-10 10:34:14',10117,10184,'a936e6b6-9df3-4447-aac4-f685b821ddeb',0,0,NULL,NULL,NULL,NULL,'text/html','10183','','','','',0,0,0,0),(10201,10192,10154,10196,'Test Test','2013-07-10 10:34:15','2013-07-10 10:34:15',10005,10196,'9d37b9be-4df9-4c58-a6b6-3544b43a96cc',0,0,NULL,NULL,NULL,NULL,'','Test Test','','','','',0,0,0,0),(10317,10310,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19',10117,10314,'613bfc55-feed-44d2-b818-a3ec9113d583',0,0,NULL,NULL,NULL,NULL,'text/html','10313','','','','',0,0,0,0),(10326,10320,10154,10158,'','2013-07-10 10:34:20','2013-07-10 10:34:20',10117,10324,'c52d4d52-45f9-4361-a1da-3334fd879003',0,0,NULL,NULL,NULL,NULL,'text/html','10323','','','','',0,0,0,0),(10335,10329,10154,10158,'','2013-07-10 10:34:20','2013-07-10 10:34:20',10117,10333,'c58a01fa-708e-4040-af5c-7e9e5f0d4eec',0,0,NULL,NULL,NULL,NULL,'text/html','10332','','','','',0,0,0,0),(10349,10338,10154,10158,'','2013-07-10 10:34:20','2013-07-10 10:34:20',10117,10347,'44e0bd97-6c52-46bc-8554-38ee5a8e291f',0,0,NULL,NULL,NULL,NULL,'text/html','10346','','','','',0,0,0,0),(10355,10338,10154,10158,'','2013-07-10 10:34:21','2013-07-10 10:34:21',10117,10353,'00fb6afd-dd5c-41b7-91cc-8e9a6b64004a',0,0,NULL,NULL,NULL,NULL,'text/html','10352','','','','',0,0,0,0),(10361,10338,10154,10158,'','2013-07-10 10:34:21','2013-07-10 10:34:21',10117,10359,'bb803370-2bd8-403e-893c-22047426d5e5',0,0,NULL,NULL,NULL,NULL,'text/html','10358','','','','',0,0,0,0),(10375,10364,10154,10158,'','2013-07-10 10:34:21','2013-07-10 10:34:21',10117,10373,'5bf1486c-2b5b-40d1-b480-d5a5211f23a7',0,0,NULL,NULL,NULL,NULL,'text/html','10372','','','','',0,0,0,0),(10383,10364,10154,10158,'','2013-07-10 10:34:21','2013-07-10 10:34:21',10117,10381,'7aecf50c-1448-4edc-b267-4c2e8e7949b4',0,0,NULL,NULL,NULL,NULL,'text/html','10380','','','','',0,0,0,0),(10389,10364,10154,10158,'','2013-07-10 10:34:21','2013-07-10 10:34:21',10117,10387,'198fb4a4-5b41-47dd-b481-cdb1a9273117',0,0,NULL,NULL,NULL,NULL,'text/html','10386','','','','',0,0,0,0),(10395,10364,10154,10158,'','2013-07-10 10:34:21','2013-07-10 10:34:21',10117,10393,'51ef9cf7-6b8d-4c29-9533-68d6db5b9fd7',0,0,NULL,NULL,NULL,NULL,'text/html','10392','','','','',0,0,0,0);
/*!40000 ALTER TABLE `AssetEntry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AssetLink`
--

DROP TABLE IF EXISTS `AssetLink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AssetLink` (
  `linkId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `entryId1` bigint(20) DEFAULT NULL,
  `entryId2` bigint(20) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  PRIMARY KEY (`linkId`),
  UNIQUE KEY `IX_8F542794` (`entryId1`,`entryId2`,`type_`),
  KEY `IX_128516C8` (`entryId1`),
  KEY `IX_56E0AB21` (`entryId1`,`entryId2`),
  KEY `IX_14D5A20D` (`entryId1`,`type_`),
  KEY `IX_12851A89` (`entryId2`),
  KEY `IX_91F132C` (`entryId2`,`type_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AssetLink`
--

LOCK TABLES `AssetLink` WRITE;
/*!40000 ALTER TABLE `AssetLink` DISABLE KEYS */;
/*!40000 ALTER TABLE `AssetLink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AssetTag`
--

DROP TABLE IF EXISTS `AssetTag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AssetTag` (
  `tagId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `assetCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`tagId`),
  KEY `IX_7C9E46BA` (`groupId`),
  KEY `IX_D63322F9` (`groupId`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AssetTag`
--

LOCK TABLES `AssetTag` WRITE;
/*!40000 ALTER TABLE `AssetTag` DISABLE KEYS */;
/*!40000 ALTER TABLE `AssetTag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AssetTagProperty`
--

DROP TABLE IF EXISTS `AssetTagProperty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AssetTagProperty` (
  `tagPropertyId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `tagId` bigint(20) DEFAULT NULL,
  `key_` varchar(75) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tagPropertyId`),
  UNIQUE KEY `IX_2C944354` (`tagId`,`key_`),
  KEY `IX_DFF1F063` (`companyId`),
  KEY `IX_13805BF7` (`companyId`,`key_`),
  KEY `IX_3269E180` (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AssetTagProperty`
--

LOCK TABLES `AssetTagProperty` WRITE;
/*!40000 ALTER TABLE `AssetTagProperty` DISABLE KEYS */;
/*!40000 ALTER TABLE `AssetTagProperty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AssetTagStats`
--

DROP TABLE IF EXISTS `AssetTagStats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AssetTagStats` (
  `tagStatsId` bigint(20) NOT NULL,
  `tagId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `assetCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`tagStatsId`),
  UNIQUE KEY `IX_56682CC4` (`tagId`,`classNameId`),
  KEY `IX_50702693` (`classNameId`),
  KEY `IX_9464CA` (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AssetTagStats`
--

LOCK TABLES `AssetTagStats` WRITE;
/*!40000 ALTER TABLE `AssetTagStats` DISABLE KEYS */;
/*!40000 ALTER TABLE `AssetTagStats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AssetVocabulary`
--

DROP TABLE IF EXISTS `AssetVocabulary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AssetVocabulary` (
  `uuid_` varchar(75) DEFAULT NULL,
  `vocabularyId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `title` longtext,
  `description` longtext,
  `settings_` longtext,
  PRIMARY KEY (`vocabularyId`),
  UNIQUE KEY `IX_C0AAD74D` (`groupId`,`name`),
  UNIQUE KEY `IX_1B2B8792` (`uuid_`,`groupId`),
  KEY `IX_B22D908C` (`companyId`),
  KEY `IX_B6B8CA0E` (`groupId`),
  KEY `IX_55F58818` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AssetVocabulary`
--

LOCK TABLES `AssetVocabulary` WRITE;
/*!40000 ALTER TABLE `AssetVocabulary` DISABLE KEYS */;
INSERT INTO `AssetVocabulary` VALUES ('53cb1350-475f-4592-add5-4a511e82d130',10316,10192,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19','Topic','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Topic</Title></root>','','');
/*!40000 ALTER TABLE `AssetVocabulary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BlogsEntry`
--

DROP TABLE IF EXISTS `BlogsEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BlogsEntry` (
  `uuid_` varchar(75) DEFAULT NULL,
  `entryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `title` varchar(150) DEFAULT NULL,
  `urlTitle` varchar(150) DEFAULT NULL,
  `description` varchar(75) DEFAULT NULL,
  `content` longtext,
  `displayDate` datetime DEFAULT NULL,
  `allowPingbacks` tinyint(4) DEFAULT NULL,
  `allowTrackbacks` tinyint(4) DEFAULT NULL,
  `trackbacks` longtext,
  `smallImage` tinyint(4) DEFAULT NULL,
  `smallImageId` bigint(20) DEFAULT NULL,
  `smallImageURL` longtext,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`entryId`),
  UNIQUE KEY `IX_DB780A20` (`groupId`,`urlTitle`),
  UNIQUE KEY `IX_1B1040FD` (`uuid_`,`groupId`),
  KEY `IX_72EF6041` (`companyId`),
  KEY `IX_430D791F` (`companyId`,`displayDate`),
  KEY `IX_BB0C2905` (`companyId`,`displayDate`,`status`),
  KEY `IX_EB2DCE27` (`companyId`,`status`),
  KEY `IX_8CACE77B` (`companyId`,`userId`),
  KEY `IX_A5F57B61` (`companyId`,`userId`,`status`),
  KEY `IX_2672F77F` (`displayDate`,`status`),
  KEY `IX_81A50303` (`groupId`),
  KEY `IX_621E19D` (`groupId`,`displayDate`),
  KEY `IX_F0E73383` (`groupId`,`displayDate`,`status`),
  KEY `IX_1EFD8EE9` (`groupId`,`status`),
  KEY `IX_FBDE0AA3` (`groupId`,`userId`,`displayDate`),
  KEY `IX_DA04F689` (`groupId`,`userId`,`displayDate`,`status`),
  KEY `IX_49E15A23` (`groupId`,`userId`,`status`),
  KEY `IX_69157A4D` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BlogsEntry`
--

LOCK TABLES `BlogsEntry` WRITE;
/*!40000 ALTER TABLE `BlogsEntry` DISABLE KEYS */;
/*!40000 ALTER TABLE `BlogsEntry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BlogsStatsUser`
--

DROP TABLE IF EXISTS `BlogsStatsUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BlogsStatsUser` (
  `statsUserId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `entryCount` int(11) DEFAULT NULL,
  `lastPostDate` datetime DEFAULT NULL,
  `ratingsTotalEntries` int(11) DEFAULT NULL,
  `ratingsTotalScore` double DEFAULT NULL,
  `ratingsAverageScore` double DEFAULT NULL,
  PRIMARY KEY (`statsUserId`),
  UNIQUE KEY `IX_82254C25` (`groupId`,`userId`),
  KEY `IX_90CDA39A` (`companyId`,`entryCount`),
  KEY `IX_43840EEB` (`groupId`),
  KEY `IX_28C78D5C` (`groupId`,`entryCount`),
  KEY `IX_BB51F1D9` (`userId`),
  KEY `IX_507BA031` (`userId`,`lastPostDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BlogsStatsUser`
--

LOCK TABLES `BlogsStatsUser` WRITE;
/*!40000 ALTER TABLE `BlogsStatsUser` DISABLE KEYS */;
/*!40000 ALTER TABLE `BlogsStatsUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BookmarksEntry`
--

DROP TABLE IF EXISTS `BookmarksEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BookmarksEntry` (
  `uuid_` varchar(75) DEFAULT NULL,
  `entryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `resourceBlockId` bigint(20) DEFAULT NULL,
  `folderId` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` longtext,
  `description` longtext,
  `visits` int(11) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`entryId`),
  UNIQUE KEY `IX_EAA02A91` (`uuid_`,`groupId`),
  KEY `IX_E52FF7EF` (`groupId`),
  KEY `IX_5200100C` (`groupId`,`folderId`),
  KEY `IX_E2E9F129` (`groupId`,`userId`),
  KEY `IX_E848278F` (`resourceBlockId`),
  KEY `IX_B670BA39` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BookmarksEntry`
--

LOCK TABLES `BookmarksEntry` WRITE;
/*!40000 ALTER TABLE `BookmarksEntry` DISABLE KEYS */;
/*!40000 ALTER TABLE `BookmarksEntry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BookmarksFolder`
--

DROP TABLE IF EXISTS `BookmarksFolder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BookmarksFolder` (
  `uuid_` varchar(75) DEFAULT NULL,
  `folderId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `resourceBlockId` bigint(20) DEFAULT NULL,
  `parentFolderId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`folderId`),
  UNIQUE KEY `IX_DC2F8927` (`uuid_`,`groupId`),
  KEY `IX_2ABA25D7` (`companyId`),
  KEY `IX_7F703619` (`groupId`),
  KEY `IX_967799C0` (`groupId`,`parentFolderId`),
  KEY `IX_28A49BB9` (`resourceBlockId`),
  KEY `IX_451E7AE3` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BookmarksFolder`
--

LOCK TABLES `BookmarksFolder` WRITE;
/*!40000 ALTER TABLE `BookmarksFolder` DISABLE KEYS */;
/*!40000 ALTER TABLE `BookmarksFolder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BrowserTracker`
--

DROP TABLE IF EXISTS `BrowserTracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BrowserTracker` (
  `browserTrackerId` bigint(20) NOT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `browserKey` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`browserTrackerId`),
  UNIQUE KEY `IX_E7B95510` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BrowserTracker`
--

LOCK TABLES `BrowserTracker` WRITE;
/*!40000 ALTER TABLE `BrowserTracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `BrowserTracker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CalEvent`
--

DROP TABLE IF EXISTS `CalEvent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CalEvent` (
  `uuid_` varchar(75) DEFAULT NULL,
  `eventId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `title` varchar(75) DEFAULT NULL,
  `description` longtext,
  `location` longtext,
  `startDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `durationHour` int(11) DEFAULT NULL,
  `durationMinute` int(11) DEFAULT NULL,
  `allDay` tinyint(4) DEFAULT NULL,
  `timeZoneSensitive` tinyint(4) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `repeating` tinyint(4) DEFAULT NULL,
  `recurrence` longtext,
  `remindBy` int(11) DEFAULT NULL,
  `firstReminder` int(11) DEFAULT NULL,
  `secondReminder` int(11) DEFAULT NULL,
  PRIMARY KEY (`eventId`),
  UNIQUE KEY `IX_5CCE79C8` (`uuid_`,`groupId`),
  KEY `IX_D6FD9496` (`companyId`),
  KEY `IX_12EE4898` (`groupId`),
  KEY `IX_4FDDD2BF` (`groupId`,`repeating`),
  KEY `IX_FCD7C63D` (`groupId`,`type_`),
  KEY `IX_FD93CBFA` (`groupId`,`type_`,`repeating`),
  KEY `IX_F6006202` (`remindBy`),
  KEY `IX_C1AD2122` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CalEvent`
--

LOCK TABLES `CalEvent` WRITE;
/*!40000 ALTER TABLE `CalEvent` DISABLE KEYS */;
/*!40000 ALTER TABLE `CalEvent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ClassName_`
--

DROP TABLE IF EXISTS `ClassName_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ClassName_` (
  `classNameId` bigint(20) NOT NULL,
  `value` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`classNameId`),
  UNIQUE KEY `IX_B27A301F` (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ClassName_`
--

LOCK TABLES `ClassName_` WRITE;
/*!40000 ALTER TABLE `ClassName_` DISABLE KEYS */;
INSERT INTO `ClassName_` VALUES (10014,'com.liferay.counter.model.Counter'),(10015,'com.liferay.portal.kernel.workflow.WorkflowTask'),(10016,'com.liferay.portal.model.Account'),(10017,'com.liferay.portal.model.Address'),(10018,'com.liferay.portal.model.BrowserTracker'),(10019,'com.liferay.portal.model.ClassName'),(10020,'com.liferay.portal.model.ClusterGroup'),(10021,'com.liferay.portal.model.Company'),(10022,'com.liferay.portal.model.Contact'),(10023,'com.liferay.portal.model.Country'),(10024,'com.liferay.portal.model.EmailAddress'),(10001,'com.liferay.portal.model.Group'),(10025,'com.liferay.portal.model.Image'),(10002,'com.liferay.portal.model.Layout'),(10026,'com.liferay.portal.model.LayoutBranch'),(10027,'com.liferay.portal.model.LayoutPrototype'),(10028,'com.liferay.portal.model.LayoutRevision'),(10029,'com.liferay.portal.model.LayoutSet'),(10030,'com.liferay.portal.model.LayoutSetBranch'),(10031,'com.liferay.portal.model.LayoutSetPrototype'),(10032,'com.liferay.portal.model.ListType'),(10033,'com.liferay.portal.model.Lock'),(10034,'com.liferay.portal.model.MembershipRequest'),(10003,'com.liferay.portal.model.Organization'),(10035,'com.liferay.portal.model.OrgGroupPermission'),(10036,'com.liferay.portal.model.OrgGroupRole'),(10037,'com.liferay.portal.model.OrgLabor'),(10038,'com.liferay.portal.model.PasswordPolicy'),(10039,'com.liferay.portal.model.PasswordPolicyRel'),(10040,'com.liferay.portal.model.PasswordTracker'),(10041,'com.liferay.portal.model.Permission'),(10042,'com.liferay.portal.model.Phone'),(10043,'com.liferay.portal.model.PluginSetting'),(10044,'com.liferay.portal.model.PortalPreferences'),(10045,'com.liferay.portal.model.Portlet'),(10046,'com.liferay.portal.model.PortletItem'),(10047,'com.liferay.portal.model.PortletPreferences'),(10048,'com.liferay.portal.model.Region'),(10049,'com.liferay.portal.model.Release'),(10050,'com.liferay.portal.model.Repository'),(10051,'com.liferay.portal.model.RepositoryEntry'),(10052,'com.liferay.portal.model.Resource'),(10053,'com.liferay.portal.model.ResourceAction'),(10054,'com.liferay.portal.model.ResourceBlock'),(10055,'com.liferay.portal.model.ResourceBlockPermission'),(10056,'com.liferay.portal.model.ResourceCode'),(10057,'com.liferay.portal.model.ResourcePermission'),(10058,'com.liferay.portal.model.ResourceTypePermission'),(10004,'com.liferay.portal.model.Role'),(10059,'com.liferay.portal.model.ServiceComponent'),(10060,'com.liferay.portal.model.Shard'),(10061,'com.liferay.portal.model.Subscription'),(10062,'com.liferay.portal.model.Team'),(10063,'com.liferay.portal.model.Ticket'),(10005,'com.liferay.portal.model.User'),(10006,'com.liferay.portal.model.UserGroup'),(10064,'com.liferay.portal.model.UserGroupGroupRole'),(10065,'com.liferay.portal.model.UserGroupRole'),(10066,'com.liferay.portal.model.UserIdMapper'),(10067,'com.liferay.portal.model.UserNotificationEvent'),(10188,'com.liferay.portal.model.UserPersonalSite'),(10068,'com.liferay.portal.model.UserTracker'),(10069,'com.liferay.portal.model.UserTrackerPath'),(10070,'com.liferay.portal.model.VirtualHost'),(10071,'com.liferay.portal.model.WebDAVProps'),(10072,'com.liferay.portal.model.Website'),(10073,'com.liferay.portal.model.WorkflowDefinitionLink'),(10074,'com.liferay.portal.model.WorkflowInstanceLink'),(10075,'com.liferay.portlet.announcements.model.AnnouncementsDelivery'),(10076,'com.liferay.portlet.announcements.model.AnnouncementsEntry'),(10077,'com.liferay.portlet.announcements.model.AnnouncementsFlag'),(10078,'com.liferay.portlet.asset.model.AssetCategory'),(10079,'com.liferay.portlet.asset.model.AssetCategoryProperty'),(10080,'com.liferay.portlet.asset.model.AssetEntry'),(10081,'com.liferay.portlet.asset.model.AssetLink'),(10082,'com.liferay.portlet.asset.model.AssetTag'),(10083,'com.liferay.portlet.asset.model.AssetTagProperty'),(10084,'com.liferay.portlet.asset.model.AssetTagStats'),(10085,'com.liferay.portlet.asset.model.AssetVocabulary'),(10007,'com.liferay.portlet.blogs.model.BlogsEntry'),(10086,'com.liferay.portlet.blogs.model.BlogsStatsUser'),(10008,'com.liferay.portlet.bookmarks.model.BookmarksEntry'),(10087,'com.liferay.portlet.bookmarks.model.BookmarksFolder'),(10009,'com.liferay.portlet.calendar.model.CalEvent'),(10088,'com.liferay.portlet.documentlibrary.model.DLContent'),(10010,'com.liferay.portlet.documentlibrary.model.DLFileEntry'),(10089,'com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata'),(10090,'com.liferay.portlet.documentlibrary.model.DLFileEntryType'),(10091,'com.liferay.portlet.documentlibrary.model.DLFileRank'),(10092,'com.liferay.portlet.documentlibrary.model.DLFileShortcut'),(10093,'com.liferay.portlet.documentlibrary.model.DLFileVersion'),(10094,'com.liferay.portlet.documentlibrary.model.DLFolder'),(10095,'com.liferay.portlet.documentlibrary.model.DLSync'),(10096,'com.liferay.portlet.dynamicdatalists.model.DDLRecord'),(10097,'com.liferay.portlet.dynamicdatalists.model.DDLRecordSet'),(10098,'com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion'),(10099,'com.liferay.portlet.dynamicdatamapping.model.DDMContent'),(10100,'com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink'),(10101,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure'),(10102,'com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink'),(10103,'com.liferay.portlet.dynamicdatamapping.model.DDMTemplate'),(10104,'com.liferay.portlet.expando.model.ExpandoColumn'),(10105,'com.liferay.portlet.expando.model.ExpandoRow'),(10106,'com.liferay.portlet.expando.model.ExpandoTable'),(10107,'com.liferay.portlet.expando.model.ExpandoValue'),(10108,'com.liferay.portlet.journal.model.JournalArticle'),(10109,'com.liferay.portlet.journal.model.JournalArticleImage'),(10110,'com.liferay.portlet.journal.model.JournalArticleResource'),(10111,'com.liferay.portlet.journal.model.JournalContentSearch'),(10112,'com.liferay.portlet.journal.model.JournalFeed'),(10113,'com.liferay.portlet.journal.model.JournalStructure'),(10114,'com.liferay.portlet.journal.model.JournalTemplate'),(10115,'com.liferay.portlet.messageboards.model.MBBan'),(10116,'com.liferay.portlet.messageboards.model.MBCategory'),(10117,'com.liferay.portlet.messageboards.model.MBDiscussion'),(10118,'com.liferay.portlet.messageboards.model.MBMailingList'),(10011,'com.liferay.portlet.messageboards.model.MBMessage'),(10119,'com.liferay.portlet.messageboards.model.MBStatsUser'),(10012,'com.liferay.portlet.messageboards.model.MBThread'),(10120,'com.liferay.portlet.messageboards.model.MBThreadFlag'),(10121,'com.liferay.portlet.mobiledevicerules.model.MDRAction'),(10122,'com.liferay.portlet.mobiledevicerules.model.MDRRule'),(10123,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup'),(10124,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance'),(10125,'com.liferay.portlet.polls.model.PollsChoice'),(10126,'com.liferay.portlet.polls.model.PollsQuestion'),(10127,'com.liferay.portlet.polls.model.PollsVote'),(10128,'com.liferay.portlet.ratings.model.RatingsEntry'),(10129,'com.liferay.portlet.ratings.model.RatingsStats'),(10130,'com.liferay.portlet.shopping.model.ShoppingCart'),(10131,'com.liferay.portlet.shopping.model.ShoppingCategory'),(10132,'com.liferay.portlet.shopping.model.ShoppingCoupon'),(10133,'com.liferay.portlet.shopping.model.ShoppingItem'),(10134,'com.liferay.portlet.shopping.model.ShoppingItemField'),(10135,'com.liferay.portlet.shopping.model.ShoppingItemPrice'),(10136,'com.liferay.portlet.shopping.model.ShoppingOrder'),(10137,'com.liferay.portlet.shopping.model.ShoppingOrderItem'),(10138,'com.liferay.portlet.social.model.SocialActivity'),(10139,'com.liferay.portlet.social.model.SocialActivityAchievement'),(10140,'com.liferay.portlet.social.model.SocialActivityCounter'),(10141,'com.liferay.portlet.social.model.SocialActivityLimit'),(10142,'com.liferay.portlet.social.model.SocialActivitySetting'),(10143,'com.liferay.portlet.social.model.SocialRelation'),(10144,'com.liferay.portlet.social.model.SocialRequest'),(10145,'com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion'),(10146,'com.liferay.portlet.softwarecatalog.model.SCLicense'),(10147,'com.liferay.portlet.softwarecatalog.model.SCProductEntry'),(10148,'com.liferay.portlet.softwarecatalog.model.SCProductScreenshot'),(10149,'com.liferay.portlet.softwarecatalog.model.SCProductVersion'),(10150,'com.liferay.portlet.usernotifications.model.UserNotificationEvent'),(10151,'com.liferay.portlet.wiki.model.WikiNode'),(10013,'com.liferay.portlet.wiki.model.WikiPage'),(10152,'com.liferay.portlet.wiki.model.WikiPageResource');
/*!40000 ALTER TABLE `ClassName_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ClusterGroup`
--

DROP TABLE IF EXISTS `ClusterGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ClusterGroup` (
  `clusterGroupId` bigint(20) NOT NULL,
  `name` varchar(75) DEFAULT NULL,
  `clusterNodeIds` varchar(75) DEFAULT NULL,
  `wholeCluster` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`clusterGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ClusterGroup`
--

LOCK TABLES `ClusterGroup` WRITE;
/*!40000 ALTER TABLE `ClusterGroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `ClusterGroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Company`
--

DROP TABLE IF EXISTS `Company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Company` (
  `companyId` bigint(20) NOT NULL,
  `accountId` bigint(20) DEFAULT NULL,
  `webId` varchar(75) DEFAULT NULL,
  `key_` longtext,
  `mx` varchar(75) DEFAULT NULL,
  `homeURL` longtext,
  `logoId` bigint(20) DEFAULT NULL,
  `system` tinyint(4) DEFAULT NULL,
  `maxUsers` int(11) DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`companyId`),
  UNIQUE KEY `IX_EC00543C` (`webId`),
  KEY `IX_38EFE3FD` (`logoId`),
  KEY `IX_12566EC2` (`mx`),
  KEY `IX_35E3E7C6` (`system`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Company`
--

LOCK TABLES `Company` WRITE;
/*!40000 ALTER TABLE `Company` DISABLE KEYS */;
INSERT INTO `Company` VALUES (10154,10156,'liferay.com','rO0ABXNyABRqYXZhLnNlY3VyaXR5LktleVJlcL35T7OImqVDAgAETAAJYWxnb3JpdGhtdAASTGphdmEvbGFuZy9TdHJpbmc7WwAHZW5jb2RlZHQAAltCTAAGZm9ybWF0cQB+AAFMAAR0eXBldAAbTGphdmEvc2VjdXJpdHkvS2V5UmVwJFR5cGU7eHB0AANERVN1cgACW0Ks8xf4BghU4AIAAHhwAAAACPJ6H1EcbepzdAADUkFXfnIAGWphdmEuc2VjdXJpdHkuS2V5UmVwJFR5cGUAAAAAAAAAABIAAHhyAA5qYXZhLmxhbmcuRW51bQAAAAAAAAAAEgAAeHB0AAZTRUNSRVQ=','liferay.com','',0,0,0,1);
/*!40000 ALTER TABLE `Company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Contact_`
--

DROP TABLE IF EXISTS `Contact_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Contact_` (
  `contactId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `accountId` bigint(20) DEFAULT NULL,
  `parentContactId` bigint(20) DEFAULT NULL,
  `firstName` varchar(75) DEFAULT NULL,
  `middleName` varchar(75) DEFAULT NULL,
  `lastName` varchar(75) DEFAULT NULL,
  `prefixId` int(11) DEFAULT NULL,
  `suffixId` int(11) DEFAULT NULL,
  `male` tinyint(4) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `smsSn` varchar(75) DEFAULT NULL,
  `aimSn` varchar(75) DEFAULT NULL,
  `facebookSn` varchar(75) DEFAULT NULL,
  `icqSn` varchar(75) DEFAULT NULL,
  `jabberSn` varchar(75) DEFAULT NULL,
  `msnSn` varchar(75) DEFAULT NULL,
  `mySpaceSn` varchar(75) DEFAULT NULL,
  `skypeSn` varchar(75) DEFAULT NULL,
  `twitterSn` varchar(75) DEFAULT NULL,
  `ymSn` varchar(75) DEFAULT NULL,
  `employeeStatusId` varchar(75) DEFAULT NULL,
  `employeeNumber` varchar(75) DEFAULT NULL,
  `jobTitle` varchar(100) DEFAULT NULL,
  `jobClass` varchar(75) DEFAULT NULL,
  `hoursOfOperation` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`contactId`),
  KEY `IX_66D496A3` (`companyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Contact_`
--

LOCK TABLES `Contact_` WRITE;
/*!40000 ALTER TABLE `Contact_` DISABLE KEYS */;
INSERT INTO `Contact_` VALUES (10159,10154,10158,'','2013-07-10 10:34:13','2013-07-10 10:34:13',10156,0,'','','',0,0,1,'2013-07-10 10:34:13','','','','','','','','','','','','','','',''),(10197,10154,10196,'','2013-07-10 10:34:15','2013-07-10 10:34:15',10156,0,'Test','','Test',0,0,1,'1970-01-01 00:00:00','','','','','','','','','','','','','','','');
/*!40000 ALTER TABLE `Contact_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Counter`
--

DROP TABLE IF EXISTS `Counter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Counter` (
  `name` varchar(75) NOT NULL,
  `currentId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Counter`
--

LOCK TABLES `Counter` WRITE;
/*!40000 ALTER TABLE `Counter` DISABLE KEYS */;
INSERT INTO `Counter` VALUES ('com.liferay.counter.model.Counter',10500),('com.liferay.portal.model.Layout#10172#true',1),('com.liferay.portal.model.Layout#10180#false',1),('com.liferay.portal.model.Layout#10310#true',1),('com.liferay.portal.model.Layout#10320#true',1),('com.liferay.portal.model.Layout#10329#true',1),('com.liferay.portal.model.Layout#10338#true',3),('com.liferay.portal.model.Layout#10364#true',4),('com.liferay.portal.model.Permission',100),('com.liferay.portal.model.Resource',100),('com.liferay.portal.model.ResourceAction',900),('com.liferay.portal.model.ResourcePermission',400);
/*!40000 ALTER TABLE `Counter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Country`
--

DROP TABLE IF EXISTS `Country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Country` (
  `countryId` bigint(20) NOT NULL,
  `name` varchar(75) DEFAULT NULL,
  `a2` varchar(75) DEFAULT NULL,
  `a3` varchar(75) DEFAULT NULL,
  `number_` varchar(75) DEFAULT NULL,
  `idd_` varchar(75) DEFAULT NULL,
  `zipRequired` tinyint(4) DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`countryId`),
  UNIQUE KEY `IX_717B97E1` (`a2`),
  UNIQUE KEY `IX_717B9BA2` (`a3`),
  UNIQUE KEY `IX_19DA007B` (`name`),
  KEY `IX_25D734CD` (`active_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Country`
--

LOCK TABLES `Country` WRITE;
/*!40000 ALTER TABLE `Country` DISABLE KEYS */;
INSERT INTO `Country` VALUES (1,'Canada','CA','CAN','124','001',1,1),(2,'China','CN','CHN','156','086',1,1),(3,'France','FR','FRA','250','033',1,1),(4,'Germany','DE','DEU','276','049',1,1),(5,'Hong Kong','HK','HKG','344','852',1,1),(6,'Hungary','HU','HUN','348','036',1,1),(7,'Israel','IL','ISR','376','972',1,1),(8,'Italy','IT','ITA','380','039',1,1),(9,'Japan','JP','JPN','392','081',1,1),(10,'South Korea','KR','KOR','410','082',1,1),(11,'Netherlands','NL','NLD','528','031',1,1),(12,'Portugal','PT','PRT','620','351',1,1),(13,'Russia','RU','RUS','643','007',1,1),(14,'Singapore','SG','SGP','702','065',1,1),(15,'Spain','ES','ESP','724','034',1,1),(16,'Turkey','TR','TUR','792','090',1,1),(17,'Vietnam','VN','VNM','704','084',1,1),(18,'United Kingdom','GB','GBR','826','044',1,1),(19,'United States','US','USA','840','001',1,1),(20,'Afghanistan','AF','AFG','4','093',1,1),(21,'Albania','AL','ALB','8','355',1,1),(22,'Algeria','DZ','DZA','12','213',1,1),(23,'American Samoa','AS','ASM','16','684',1,1),(24,'Andorra','AD','AND','20','376',1,1),(25,'Angola','AO','AGO','24','244',0,1),(26,'Anguilla','AI','AIA','660','264',1,1),(27,'Antarctica','AQ','ATA','10','672',1,1),(28,'Antigua','AG','ATG','28','268',0,1),(29,'Argentina','AR','ARG','32','054',1,1),(30,'Armenia','AM','ARM','51','374',1,1),(31,'Aruba','AW','ABW','533','297',0,1),(32,'Australia','AU','AUS','36','061',1,1),(33,'Austria','AT','AUT','40','043',1,1),(34,'Azerbaijan','AZ','AZE','31','994',1,1),(35,'Bahamas','BS','BHS','44','242',0,1),(36,'Bahrain','BH','BHR','48','973',1,1),(37,'Bangladesh','BD','BGD','50','880',1,1),(38,'Barbados','BB','BRB','52','246',1,1),(39,'Belarus','BY','BLR','112','375',1,1),(40,'Belgium','BE','BEL','56','032',1,1),(41,'Belize','BZ','BLZ','84','501',0,1),(42,'Benin','BJ','BEN','204','229',0,1),(43,'Bermuda','BM','BMU','60','441',1,1),(44,'Bhutan','BT','BTN','64','975',1,1),(45,'Bolivia','BO','BOL','68','591',1,1),(46,'Bosnia-Herzegovina','BA','BIH','70','387',1,1),(47,'Botswana','BW','BWA','72','267',0,1),(48,'Brazil','BR','BRA','76','055',1,1),(49,'British Virgin Islands','VG','VGB','92','284',1,1),(50,'Brunei','BN','BRN','96','673',1,1),(51,'Bulgaria','BG','BGR','100','359',1,1),(52,'Burkina Faso','BF','BFA','854','226',0,1),(53,'Burma (Myanmar)','MM','MMR','104','095',1,1),(54,'Burundi','BI','BDI','108','257',0,1),(55,'Cambodia','KH','KHM','116','855',1,1),(56,'Cameroon','CM','CMR','120','237',1,1),(57,'Cape Verde Island','CV','CPV','132','238',1,1),(58,'Cayman Islands','KY','CYM','136','345',1,1),(59,'Central African Republic','CF','CAF','140','236',0,1),(60,'Chad','TD','TCD','148','235',1,1),(61,'Chile','CL','CHL','152','056',1,1),(62,'Christmas Island','CX','CXR','162','061',1,1),(63,'Cocos Islands','CC','CCK','166','061',1,1),(64,'Colombia','CO','COL','170','057',1,1),(65,'Comoros','KM','COM','174','269',0,1),(66,'Republic of Congo','CD','COD','180','242',0,1),(67,'Democratic Republic of Congo','CG','COG','178','243',0,1),(68,'Cook Islands','CK','COK','184','682',0,1),(69,'Costa Rica','CR','CRI','188','506',1,1),(70,'Croatia','HR','HRV','191','385',1,1),(71,'Cuba','CU','CUB','192','053',1,1),(72,'Cyprus','CY','CYP','196','357',1,1),(73,'Czech Republic','CZ','CZE','203','420',1,1),(74,'Denmark','DK','DNK','208','045',1,1),(75,'Djibouti','DJ','DJI','262','253',0,1),(76,'Dominica','DM','DMA','212','767',0,1),(77,'Dominican Republic','DO','DOM','214','809',1,1),(78,'Ecuador','EC','ECU','218','593',1,1),(79,'Egypt','EG','EGY','818','020',1,1),(80,'El Salvador','SV','SLV','222','503',1,1),(81,'Equatorial Guinea','GQ','GNQ','226','240',0,1),(82,'Eritrea','ER','ERI','232','291',0,1),(83,'Estonia','EE','EST','233','372',1,1),(84,'Ethiopia','ET','ETH','231','251',1,1),(85,'Faeroe Islands','FO','FRO','234','298',1,1),(86,'Falkland Islands','FK','FLK','238','500',1,1),(87,'Fiji Islands','FJ','FJI','242','679',0,1),(88,'Finland','FI','FIN','246','358',1,1),(89,'French Guiana','GF','GUF','254','594',1,1),(90,'French Polynesia','PF','PYF','258','689',1,1),(91,'Gabon','GA','GAB','266','241',1,1),(92,'Gambia','GM','GMB','270','220',0,1),(93,'Georgia','GE','GEO','268','995',1,1),(94,'Ghana','GH','GHA','288','233',0,1),(95,'Gibraltar','GI','GIB','292','350',1,1),(96,'Greece','GR','GRC','300','030',1,1),(97,'Greenland','GL','GRL','304','299',1,1),(98,'Grenada','GD','GRD','308','473',0,1),(99,'Guadeloupe','GP','GLP','312','590',1,1),(100,'Guam','GU','GUM','316','671',1,1),(101,'Guatemala','GT','GTM','320','502',1,1),(102,'Guinea','GN','GIN','324','224',0,1),(103,'Guinea-Bissau','GW','GNB','624','245',1,1),(104,'Guyana','GY','GUY','328','592',0,1),(105,'Haiti','HT','HTI','332','509',1,1),(106,'Honduras','HN','HND','340','504',1,1),(107,'Iceland','IS','ISL','352','354',1,1),(108,'India','IN','IND','356','091',1,1),(109,'Indonesia','ID','IDN','360','062',1,1),(110,'Iran','IR','IRN','364','098',1,1),(111,'Iraq','IQ','IRQ','368','964',1,1),(112,'Ireland','IE','IRL','372','353',0,1),(113,'Ivory Coast','CI','CIV','384','225',1,1),(114,'Jamaica','JM','JAM','388','876',1,1),(115,'Jordan','JO','JOR','400','962',1,1),(116,'Kazakhstan','KZ','KAZ','398','007',1,1),(117,'Kenya','KE','KEN','404','254',1,1),(118,'Kiribati','KI','KIR','408','686',0,1),(119,'Kuwait','KW','KWT','414','965',1,1),(120,'North Korea','KP','PRK','408','850',0,1),(121,'Kyrgyzstan','KG','KGZ','471','996',1,1),(122,'Laos','LA','LAO','418','856',1,1),(123,'Latvia','LV','LVA','428','371',1,1),(124,'Lebanon','LB','LBN','422','961',1,1),(125,'Lesotho','LS','LSO','426','266',1,1),(126,'Liberia','LR','LBR','430','231',1,1),(127,'Libya','LY','LBY','434','218',1,1),(128,'Liechtenstein','LI','LIE','438','423',1,1),(129,'Lithuania','LT','LTU','440','370',1,1),(130,'Luxembourg','LU','LUX','442','352',1,1),(131,'Macau','MO','MAC','446','853',0,1),(132,'Macedonia','MK','MKD','807','389',1,1),(133,'Madagascar','MG','MDG','450','261',1,1),(134,'Malawi','MW','MWI','454','265',0,1),(135,'Malaysia','MY','MYS','458','060',1,1),(136,'Maldives','MV','MDV','462','960',1,1),(137,'Mali','ML','MLI','466','223',0,1),(138,'Malta','MT','MLT','470','356',1,1),(139,'Marshall Islands','MH','MHL','584','692',1,1),(140,'Martinique','MQ','MTQ','474','596',1,1),(141,'Mauritania','MR','MRT','478','222',0,1),(142,'Mauritius','MU','MUS','480','230',0,1),(143,'Mayotte Island','YT','MYT','175','269',1,1),(144,'Mexico','MX','MEX','484','052',1,1),(145,'Micronesia','FM','FSM','583','691',1,1),(146,'Moldova','MD','MDA','498','373',1,1),(147,'Monaco','MC','MCO','492','377',1,1),(148,'Mongolia','MN','MNG','496','976',1,1),(149,'Montenegro','ME','MNE','499','382',1,1),(150,'Montserrat','MS','MSR','500','664',0,1),(151,'Morocco','MA','MAR','504','212',1,1),(152,'Mozambique','MZ','MOZ','508','258',1,1),(153,'Namibia','NA','NAM','516','264',1,1),(154,'Nauru','NR','NRU','520','674',0,1),(155,'Nepal','NP','NPL','524','977',1,1),(156,'Netherlands Antilles','AN','ANT','530','599',1,1),(157,'New Caledonia','NC','NCL','540','687',1,1),(158,'New Zealand','NZ','NZL','554','064',1,1),(159,'Nicaragua','NI','NIC','558','505',1,1),(160,'Niger','NE','NER','562','227',1,1),(161,'Nigeria','NG','NGA','566','234',1,1),(162,'Niue','NU','NIU','570','683',0,1),(163,'Norfolk Island','NF','NFK','574','672',1,1),(164,'Norway','NO','NOR','578','047',1,1),(165,'Oman','OM','OMN','512','968',1,1),(166,'Pakistan','PK','PAK','586','092',1,1),(167,'Palau','PW','PLW','585','680',1,1),(168,'Palestine','PS','PSE','275','970',1,1),(169,'Panama','PA','PAN','591','507',1,1),(170,'Papua New Guinea','PG','PNG','598','675',1,1),(171,'Paraguay','PY','PRY','600','595',1,1),(172,'Peru','PE','PER','604','051',1,1),(173,'Philippines','PH','PHL','608','063',1,1),(174,'Poland','PL','POL','616','048',1,1),(175,'Puerto Rico','PR','PRI','630','787',1,1),(176,'Qatar','QA','QAT','634','974',0,1),(177,'Reunion Island','RE','REU','638','262',1,1),(178,'Romania','RO','ROU','642','040',1,1),(179,'Rwanda','RW','RWA','646','250',0,1),(180,'St. Helena','SH','SHN','654','290',1,1),(181,'St. Kitts','KN','KNA','659','869',0,1),(182,'St. Lucia','LC','LCA','662','758',0,1),(183,'St. Pierre & Miquelon','PM','SPM','666','508',1,1),(184,'St. Vincent','VC','VCT','670','784',1,1),(185,'San Marino','SM','SMR','674','378',1,1),(186,'Sao Tome & Principe','ST','STP','678','239',0,1),(187,'Saudi Arabia','SA','SAU','682','966',1,1),(188,'Senegal','SN','SEN','686','221',1,1),(189,'Serbia','RS','SRB','688','381',1,1),(190,'Seychelles','SC','SYC','690','248',0,1),(191,'Sierra Leone','SL','SLE','694','249',0,1),(192,'Slovakia','SK','SVK','703','421',1,1),(193,'Slovenia','SI','SVN','705','386',1,1),(194,'Solomon Islands','SB','SLB','90','677',0,1),(195,'Somalia','SO','SOM','706','252',0,1),(196,'South Africa','ZA','ZAF','710','027',1,1),(197,'Sri Lanka','LK','LKA','144','094',1,1),(198,'Sudan','SD','SDN','736','095',1,1),(199,'Suriname','SR','SUR','740','597',0,1),(200,'Swaziland','SZ','SWZ','748','268',1,1),(201,'Sweden','SE','SWE','752','046',1,1),(202,'Switzerland','CH','CHE','756','041',1,1),(203,'Syria','SY','SYR','760','963',0,1),(204,'Taiwan','TW','TWN','158','886',1,1),(205,'Tajikistan','TJ','TJK','762','992',1,1),(206,'Tanzania','TZ','TZA','834','255',0,1),(207,'Thailand','TH','THA','764','066',1,1),(208,'Togo','TG','TGO','768','228',1,1),(209,'Tonga','TO','TON','776','676',0,1),(210,'Trinidad & Tobago','TT','TTO','780','868',0,1),(211,'Tunisia','TN','TUN','788','216',1,1),(212,'Turkmenistan','TM','TKM','795','993',1,1),(213,'Turks & Caicos','TC','TCA','796','649',1,1),(214,'Tuvalu','TV','TUV','798','688',0,1),(215,'Uganda','UG','UGA','800','256',0,1),(216,'Ukraine','UA','UKR','804','380',1,1),(217,'United Arab Emirates','AE','ARE','784','971',0,1),(218,'Uruguay','UY','URY','858','598',1,1),(219,'Uzbekistan','UZ','UZB','860','998',1,1),(220,'Vanuatu','VU','VUT','548','678',0,1),(221,'Vatican City','VA','VAT','336','039',1,1),(222,'Venezuela','VE','VEN','862','058',1,1),(223,'Wallis & Futuna','WF','WLF','876','681',1,1),(224,'Western Samoa','EH','ESH','732','685',1,1),(225,'Yemen','YE','YEM','887','967',0,1),(226,'Zambia','ZM','ZMB','894','260',1,1),(227,'Zimbabwe','ZW','ZWE','716','263',0,1);
/*!40000 ALTER TABLE `Country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CyrusUser`
--

DROP TABLE IF EXISTS `CyrusUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CyrusUser` (
  `userId` varchar(75) NOT NULL,
  `password_` varchar(75) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CyrusUser`
--

LOCK TABLES `CyrusUser` WRITE;
/*!40000 ALTER TABLE `CyrusUser` DISABLE KEYS */;
/*!40000 ALTER TABLE `CyrusUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CyrusVirtual`
--

DROP TABLE IF EXISTS `CyrusVirtual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CyrusVirtual` (
  `emailAddress` varchar(75) NOT NULL,
  `userId` varchar(75) NOT NULL,
  PRIMARY KEY (`emailAddress`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CyrusVirtual`
--

LOCK TABLES `CyrusVirtual` WRITE;
/*!40000 ALTER TABLE `CyrusVirtual` DISABLE KEYS */;
/*!40000 ALTER TABLE `CyrusVirtual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DDLRecord`
--

DROP TABLE IF EXISTS `DDLRecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DDLRecord` (
  `uuid_` varchar(75) DEFAULT NULL,
  `recordId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `versionUserId` bigint(20) DEFAULT NULL,
  `versionUserName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `DDMStorageId` bigint(20) DEFAULT NULL,
  `recordSetId` bigint(20) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  `displayIndex` int(11) DEFAULT NULL,
  PRIMARY KEY (`recordId`),
  UNIQUE KEY `IX_B4328F39` (`uuid_`,`groupId`),
  KEY `IX_87A6B599` (`recordSetId`),
  KEY `IX_AAC564D3` (`recordSetId`,`userId`),
  KEY `IX_8BC2F891` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DDLRecord`
--

LOCK TABLES `DDLRecord` WRITE;
/*!40000 ALTER TABLE `DDLRecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `DDLRecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DDLRecordSet`
--

DROP TABLE IF EXISTS `DDLRecordSet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DDLRecordSet` (
  `uuid_` varchar(75) DEFAULT NULL,
  `recordSetId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `DDMStructureId` bigint(20) DEFAULT NULL,
  `recordSetKey` varchar(75) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `minDisplayRows` int(11) DEFAULT NULL,
  `scope` int(11) DEFAULT NULL,
  PRIMARY KEY (`recordSetId`),
  UNIQUE KEY `IX_56DAB121` (`groupId`,`recordSetKey`),
  UNIQUE KEY `IX_270BA5E1` (`uuid_`,`groupId`),
  KEY `IX_4FA5969F` (`groupId`),
  KEY `IX_561E44E9` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DDLRecordSet`
--

LOCK TABLES `DDLRecordSet` WRITE;
/*!40000 ALTER TABLE `DDLRecordSet` DISABLE KEYS */;
/*!40000 ALTER TABLE `DDLRecordSet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DDLRecordVersion`
--

DROP TABLE IF EXISTS `DDLRecordVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DDLRecordVersion` (
  `recordVersionId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `DDMStorageId` bigint(20) DEFAULT NULL,
  `recordSetId` bigint(20) DEFAULT NULL,
  `recordId` bigint(20) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  `displayIndex` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`recordVersionId`),
  UNIQUE KEY `IX_C79E347` (`recordId`,`version`),
  KEY `IX_2F4DDFE1` (`recordId`),
  KEY `IX_762ADC7` (`recordId`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DDLRecordVersion`
--

LOCK TABLES `DDLRecordVersion` WRITE;
/*!40000 ALTER TABLE `DDLRecordVersion` DISABLE KEYS */;
/*!40000 ALTER TABLE `DDLRecordVersion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DDMContent`
--

DROP TABLE IF EXISTS `DDMContent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DDMContent` (
  `uuid_` varchar(75) DEFAULT NULL,
  `contentId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `xml` longtext,
  PRIMARY KEY (`contentId`),
  UNIQUE KEY `IX_EB9BDE28` (`uuid_`,`groupId`),
  KEY `IX_E3BAF436` (`companyId`),
  KEY `IX_50BF1038` (`groupId`),
  KEY `IX_AE4B50C2` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DDMContent`
--

LOCK TABLES `DDMContent` WRITE;
/*!40000 ALTER TABLE `DDMContent` DISABLE KEYS */;
/*!40000 ALTER TABLE `DDMContent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DDMStorageLink`
--

DROP TABLE IF EXISTS `DDMStorageLink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DDMStorageLink` (
  `uuid_` varchar(75) DEFAULT NULL,
  `storageLinkId` bigint(20) NOT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `structureId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`storageLinkId`),
  UNIQUE KEY `IX_702D1AD5` (`classPK`),
  KEY `IX_81776090` (`structureId`),
  KEY `IX_32A18526` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DDMStorageLink`
--

LOCK TABLES `DDMStorageLink` WRITE;
/*!40000 ALTER TABLE `DDMStorageLink` DISABLE KEYS */;
/*!40000 ALTER TABLE `DDMStorageLink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DDMStructure`
--

DROP TABLE IF EXISTS `DDMStructure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DDMStructure` (
  `uuid_` varchar(75) DEFAULT NULL,
  `structureId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `structureKey` varchar(75) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `xsd` longtext,
  `storageType` varchar(75) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  PRIMARY KEY (`structureId`),
  UNIQUE KEY `IX_490E7A1E` (`groupId`,`structureKey`),
  UNIQUE KEY `IX_85C7EBE2` (`uuid_`,`groupId`),
  KEY `IX_31817A62` (`classNameId`),
  KEY `IX_C8419FBE` (`groupId`),
  KEY `IX_E61809C8` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DDMStructure`
--

LOCK TABLES `DDMStructure` WRITE;
/*!40000 ALTER TABLE `DDMStructure` DISABLE KEYS */;
INSERT INTO `DDMStructure` VALUES ('3dd1993d-389e-4376-a7ec-f69fc0b68833',10297,10192,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19',10089,'Learning Module Metadata','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Learning Module Metadata</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Learning Module Metadata</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"select2235\" type=\"select\">\n		<dynamic-element name=\"home_edition\" type=\"option\" value=\"HE\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Home Edition]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"business_edition\" type=\"option\" value=\"BE\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Business Edition]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"enterprise_edition\" type=\"option\" value=\"EE\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Enterprise Edition]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Product]]></entry>\n			<entry name=\"multiple\"><![CDATA[true]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select3212\" type=\"select\">\n		<dynamic-element name=\"1_0\" type=\"option\" value=\"1\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[1.0]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"2_0\" type=\"option\" value=\"2\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[2.0]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"3_0\" type=\"option\" value=\"3\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[3.0]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Version]]></entry>\n			<entry name=\"multiple\"><![CDATA[true]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select4115\" type=\"select\">\n		<dynamic-element name=\"administration\" type=\"option\" value=\"admin\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Administration]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"installation\" type=\"option\" value=\"install\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Installation]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"configuration\" type=\"option\" value=\"config\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Configuration]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Topics]]></entry>\n			<entry name=\"multiple\"><![CDATA[true]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select5069\" type=\"select\">\n		<dynamic-element name=\"beginner\" type=\"option\" value=\"beginner\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Beginner]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"intermediate\" type=\"option\" value=\"intermediate\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Intermediate]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"advanced\" type=\"option\" value=\"advanced\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Advanced]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Level]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('4b25005f-f0fe-46fe-a9f2-77d714c1de5d',10298,10192,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19',10089,'Marketing Campaign Theme Metadata','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Marketing Campaign Theme Metadata</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Marketing Campaign Theme Metadata</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"select2305\" type=\"select\">\n		<dynamic-element name=\"strong_company\" type=\"option\" value=\"strong\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Strong Company]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"new_product_launch\" type=\"option\" value=\"product\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[New Product Launch]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"company_philosophy\" type=\"option\" value=\"philosophy\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Company Philosophy]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Select]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select3229\" type=\"select\">\n		<dynamic-element name=\"your_trusted_advisor\" type=\"option\" value=\"advisor\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Your Trusted Advisor]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"10_years_of_customer_solutions\" type=\"option\" value=\"solutions\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[10 Years of Customer Solutions]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"making_a_difference\" type=\"option\" value=\"difference\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Making a Difference]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Campaign Theme]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select4282\" type=\"select\">\n		<dynamic-element name=\"awareness\" type=\"option\" value=\"awareness\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Awareness]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"lead_generation\" type=\"option\" value=\"leads\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Lead Generation]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"customer_service\" type=\"option\" value=\"service\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Customer Service]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Business Goal]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('5c63a815-86cf-466b-acd5-a111a0b81c91',10299,10192,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19',10089,'Meeting Metadata','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Meeting Metadata</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Metadata for meeting</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"ddm-date3054\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text2217\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Meeting Name]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text4569\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Time]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text5638\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Location]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"textarea6584\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"textarea7502\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Participants]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('524b565b-4f6f-4f01-921b-149603215b80',10301,10192,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19',10089,'auto_3aece580-416e-4df5-bbba-06db2590445c','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Contract</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Legal Contracts</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"ddm-date18949\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Effective Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"ddm-date20127\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Expiration Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select10264\" type=\"select\">\n		<dynamic-element name=\"nda\" type=\"option\" value=\"NDA\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[NDA]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"msa\" type=\"option\" value=\"MSA\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[MSA]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"license_agreement\" type=\"option\" value=\"License\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[License Agreement]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Contract Type]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select4893\" type=\"select\">\n		<dynamic-element name=\"draft\" type=\"option\" value=\"Draft\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Draft]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"in_review\" type=\"option\" value=\"Review\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[In Review]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"suspended\" type=\"option\" value=\"Suspended\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Suspended]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"signed\" type=\"option\" value=\"Signed\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Signed]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Status]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text14822\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Legal Reviewer]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text17700\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Signing Authority]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text2087\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Deal Name]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',1),('ad123ca2-542e-43ba-8fd0-447b6c25a626',10303,10192,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19',10089,'auto_83d20ed4-5204-46f7-a7c2-dbc5fd5b1405','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Marketing Banner</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Marketing Banner</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"radio5547\" type=\"radio\">\n		<dynamic-element name=\"yes\" type=\"option\" value=\"yes\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Yes]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"no\" type=\"option\" value=\"no\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[No]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Needs Legal Review]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text2033\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Banner Name]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"textarea2873\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',1),('852934f5-df2b-4de8-b5c8-c9c14e330daa',10305,10192,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19',10089,'auto_82eedb91-da6a-439f-b6cc-b33a031add1a','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Online Training</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Online Training</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"text2082\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Lesson Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text2979\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Author]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',1),('8b4b3c34-896e-402c-8bde-f32caa24a4a2',10307,10192,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19',10089,'auto_57e2156a-3e91-4745-bd3d-432012510947','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Sales Presentation</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Sales Presentation</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"select2890\" type=\"select\">\n		<dynamic-element name=\"home_edition\" type=\"option\" value=\"HE\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Home Edition]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"business_edition\" type=\"option\" value=\"BE\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Business Edition]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"enterprise_edition\" type=\"option\" value=\"EE\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Enterprise Edition]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Product]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select3864\" type=\"select\">\n		<dynamic-element name=\"1_0\" type=\"option\" value=\"1\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[1.0]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"2_0\" type=\"option\" value=\"2\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[2.0]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"3_0\" type=\"option\" value=\"3\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[3.0]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Version]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select4831\" type=\"select\">\n		<dynamic-element name=\"website\" type=\"option\" value=\"website\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Website]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"collaboration\" type=\"option\" value=\"collaboration\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Collaboration]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"intranet\" type=\"option\" value=\"intranet\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Intranet]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Areas of Interest]]></entry>\n			<entry name=\"multiple\"><![CDATA[true]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select5929\" type=\"select\">\n		<dynamic-element name=\"acme\" type=\"option\" value=\"acme\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[ACME]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"sevencogs\" type=\"option\" value=\"sevencogs\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[SevenCogs]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"freeplus\" type=\"option\" value=\"freeplus\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[FreePlus]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Competitors]]></entry>\n			<entry name=\"multiple\"><![CDATA[true]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text1993\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Prospect Name]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',1),('8bf114dd-1e1e-4f53-ae27-fe9cd959b854',10308,10192,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19',10010,'TikaRawMetadata','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">TikaRawMetadata</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">TikaRawMetadata</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_PROGRAM_ID\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.PROGRAM_ID]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_COMMAND_LINE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.COMMAND_LINE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_HISTORY\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.HISTORY]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_TABLE_ID\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.TABLE_ID]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_INSTITUTION\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.INSTITUTION]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_SOURCE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.SOURCE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_CONTACT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.CONTACT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_PROJECT_ID\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.PROJECT_ID]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_CONVENTIONS\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.CONVENTIONS]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_REFERENCES\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.REFERENCES]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_ACKNOWLEDGEMENT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.ACKNOWLEDGEMENT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_REALIZATION\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.REALIZATION]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_EXPERIMENT_ID\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.EXPERIMENT_ID]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_COMMENT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.COMMENT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"ClimateForcast_MODEL_NAME_ENGLISH\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.ClimateForcast.MODEL_NAME_ENGLISH]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"CreativeCommons_LICENSE_URL\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.CreativeCommons.LICENSE_URL]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"CreativeCommons_LICENSE_LOCATION\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.CreativeCommons.LICENSE_LOCATION]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"CreativeCommons_WORK_TYPE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.CreativeCommons.WORK_TYPE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_FORMAT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.FORMAT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_IDENTIFIER\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.IDENTIFIER]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_MODIFIED\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.MODIFIED]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_CONTRIBUTOR\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.CONTRIBUTOR]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_COVERAGE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.COVERAGE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_CREATOR\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.CREATOR]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_DATE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.DATE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_DESCRIPTION\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.DESCRIPTION]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_LANGUAGE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.LANGUAGE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_PUBLISHER\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.PUBLISHER]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_RELATION\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.RELATION]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_RIGHTS\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.RIGHTS]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_SOURCE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.SOURCE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_SUBJECT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.SUBJECT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_TITLE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.TITLE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"DublinCore_TYPE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.DublinCore.TYPE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"Geographic_LATITUDE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.Geographic.LATITUDE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"Geographic_LONGITUDE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.Geographic.LONGITUDE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"Geographic_ALTITUDE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.Geographic.ALTITUDE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"HttpHeaders_CONTENT_ENCODING\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.HttpHeaders.CONTENT_ENCODING]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"HttpHeaders_CONTENT_LANGUAGE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.HttpHeaders.CONTENT_LANGUAGE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"HttpHeaders_CONTENT_LENGTH\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.HttpHeaders.CONTENT_LENGTH]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"HttpHeaders_CONTENT_LOCATION\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.HttpHeaders.CONTENT_LOCATION]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"HttpHeaders_CONTENT_DISPOSITION\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.HttpHeaders.CONTENT_DISPOSITION]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"HttpHeaders_CONTENT_MD5\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.HttpHeaders.CONTENT_MD5]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"HttpHeaders_CONTENT_TYPE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.HttpHeaders.CONTENT_TYPE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"HttpHeaders_LAST_MODIFIED\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.HttpHeaders.LAST_MODIFIED]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"HttpHeaders_LOCATION\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.HttpHeaders.LOCATION]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"Message_MESSAGE_RECIPIENT_ADDRESS\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.Message.MESSAGE_RECIPIENT_ADDRESS]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"Message_MESSAGE_FROM\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.Message.MESSAGE_FROM]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"Message_MESSAGE_TO\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.Message.MESSAGE_TO]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"Message_MESSAGE_CC\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.Message.MESSAGE_CC]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"Message_MESSAGE_BCC\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.Message.MESSAGE_BCC]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_KEYWORDS\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.KEYWORDS]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_COMMENTS\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.COMMENTS]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_LAST_AUTHOR\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.LAST_AUTHOR]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_APPLICATION_NAME\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.APPLICATION_NAME]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_CHARACTER_COUNT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.CHARACTER_COUNT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_LAST_PRINTED\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.LAST_PRINTED]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_LAST_SAVED\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.LAST_SAVED]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_PAGE_COUNT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.PAGE_COUNT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_REVISION_NUMBER\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.REVISION_NUMBER]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_WORD_COUNT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.WORD_COUNT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_TEMPLATE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.TEMPLATE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_AUTHOR\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.AUTHOR]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_TOTAL_TIME\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.TOTAL_TIME]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_SLIDE_COUNT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.SLIDE_COUNT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_PRESENTATION_FORMAT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.PRESENTATION_FORMAT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_PARAGRAPH_COUNT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.PARAGRAPH_COUNT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_NOTES\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.NOTES]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_MANAGER\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.MANAGER]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_LINE_COUNT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.LINE_COUNT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_CHARACTER_COUNT_WITH_SPACES\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.CHARACTER_COUNT_WITH_SPACES]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_APPLICATION_VERSION\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.APPLICATION_VERSION]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_VERSION\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.VERSION]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_CONTENT_STATUS\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.CONTENT_STATUS]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_CATEGORY\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.CATEGORY]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_COMPANY\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.COMPANY]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_SECURITY\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.SECURITY]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_EDIT_TIME\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.EDIT_TIME]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"MSOffice_CREATION_DATE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.MSOffice.CREATION_DATE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_BITS_PER_SAMPLE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.BITS_PER_SAMPLE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_IMAGE_LENGTH\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.IMAGE_LENGTH]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_IMAGE_WIDTH\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.IMAGE_WIDTH]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_SAMPLES_PER_PIXEL\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.SAMPLES_PER_PIXEL]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_FLASH_FIRED\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.FLASH_FIRED]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_EXPOSURE_TIME\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.EXPOSURE_TIME]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_F_NUMBER\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.F_NUMBER]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_FOCAL_LENGTH\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.FOCAL_LENGTH]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_ISO_SPEED_RATINGS\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.ISO_SPEED_RATINGS]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_EQUIPMENT_MAKE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.EQUIPMENT_MAKE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_EQUIPMENT_MODEL\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.EQUIPMENT_MODEL]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_SOFTWARE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.SOFTWARE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_ORIENTATION\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.ORIENTATION]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_RESOLUTION_HORIZONTAL\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.RESOLUTION_HORIZONTAL]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_RESOLUTION_VERTICAL\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.RESOLUTION_VERTICAL]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_RESOLUTION_UNIT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.RESOLUTION_UNIT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TIFF_ORIGINAL_DATE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TIFF.ORIGINAL_DATE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TikaMetadataKeys_RESOURCE_NAME_KEY\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TikaMetadataKeys.RESOURCE_NAME_KEY]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TikaMetadataKeys_PROTECTED\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TikaMetadataKeys.PROTECTED]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TikaMimeKeys_TIKA_MIME_FILE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TikaMimeKeys.TIKA_MIME_FILE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"TikaMimeKeys_MIME_TYPE_MAGIC\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.TikaMimeKeys.MIME_TYPE_MAGIC]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_DURATION\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.DURATION]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_ABS_PEAK_AUDIO_FILE_PATH\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.ABS_PEAK_AUDIO_FILE_PATH]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_ALBUM\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.ALBUM]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_ALT_TAPE_NAME\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.ALT_TAPE_NAME]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_ARTIST\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.ARTIST]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_AUDIO_MOD_DATE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.AUDIO_MOD_DATE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_AUDIO_SAMPLE_RATE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.AUDIO_SAMPLE_RATE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_AUDIO_SAMPLE_TYPE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.AUDIO_SAMPLE_TYPE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_AUDIO_CHANNEL_TYPE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.AUDIO_CHANNEL_TYPE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_AUDIO_COMPRESSOR\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.AUDIO_COMPRESSOR]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_COMPOSER\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.COMPOSER]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_COPYRIGHT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.COPYRIGHT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_ENGINEER\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.ENGINEER]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_FILE_DATA_RATE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.FILE_DATA_RATE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_GENRE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.GENRE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_INSTRUMENT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.INSTRUMENT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_KEY\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.KEY]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_LOG_COMMENT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.LOG_COMMENT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_LOOP\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.LOOP]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_NUMBER_OF_BEATS\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.NUMBER_OF_BEATS]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_METADATA_MOD_DATE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.METADATA_MOD_DATE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_PULL_DOWN\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.PULL_DOWN]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_RELATIVE_PEAK_AUDIO_FILE_PATH\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.RELATIVE_PEAK_AUDIO_FILE_PATH]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_RELEASE_DATE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.RELEASE_DATE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_SCALE_TYPE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.SCALE_TYPE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_SCENE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.SCENE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_SHOT_DATE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.SHOT_DATE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_SHOT_LOCATION\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.SHOT_LOCATION]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_SHOT_NAME\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.SHOT_NAME]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_SPEAKER_PLACEMENT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.SPEAKER_PLACEMENT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_STRETCH_MODE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.STRETCH_MODE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_TAPE_NAME\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.TAPE_NAME]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_TEMPO\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.TEMPO]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_TIME_SIGNATURE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.TIME_SIGNATURE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_TRACK_NUMBER\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.TRACK_NUMBER]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_ALPHA_MODE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_ALPHA_MODE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_ALPHA_UNITY_IS_TRANSPARENT\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_ALPHA_UNITY_IS_TRANSPARENT]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_COLOR_SPACE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_COLOR_SPACE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_COMPRESSOR\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_COMPRESSOR]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_FIELD_ORDER\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_FIELD_ORDER]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_FRAME_RATE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_FRAME_RATE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_MOD_DATE\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_MOD_DATE]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_PIXEL_DEPTH\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_PIXEL_DEPTH]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_PIXEL_ASPECT_RATIO\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_PIXEL_ASPECT_RATIO]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('7d2005f6-3681-4ddd-8f21-3e4e4a0b2971',10399,10180,10154,10158,'','2013-07-10 10:34:22','2013-07-10 10:34:22',10097,'Contacts','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Contacts</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Contacts</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"company\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Company]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"email\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Email]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"firstName\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[First Name]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"imService\" type=\"select\">\n		<dynamic-element name=\"aol\" type=\"option\" value=\"aol\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[AOL]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"yahoo\" type=\"option\" value=\"yahoo\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Yahoo]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"gtalk\" type=\"option\" value=\"gtalk\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[GTalk]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Instant Messenger Service]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[[\"gtalk\"]]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"imUserName\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Instant Messenger]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"jobTitle\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Job Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"lastName\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Last Name]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"notes\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Notes]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"phoneMobile\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Phone (Mobile)]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"phoneOffice\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Phone (Office)]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('5a843e62-c83a-40a1-9fce-370bf7a16c88',10400,10180,10154,10158,'','2013-07-10 10:34:22','2013-07-10 10:34:22',10097,'Events','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Events</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Events</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"file-upload\" fieldNamespace=\"ddm\" name=\"attachment\" type=\"ddm-fileupload\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"acceptFiles\"><![CDATA[*]]></entry>\n			<entry name=\"folder\"><![CDATA[{\"folderId\":0,\"folderName\":\"Documents Home\"}]]></entry>\n			<entry name=\"label\"><![CDATA[Attachment]]></entry>\n			<entry name=\"name\"><![CDATA[attachment]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[Upload documents no larger than 3,000k.]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-fileupload]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"double\" fieldNamespace=\"ddm\" name=\"cost\" type=\"ddm-number\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Cost]]></entry>\n			<entry name=\"name\"><![CDATA[cost]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-number]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"description\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"name\"><![CDATA[description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[textarea]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"eventDate\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Date]]></entry>\n			<entry name=\"name\"><![CDATA[eventDate]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-date]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"eventName\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Event Name]]></entry>\n			<entry name=\"name\"><![CDATA[eventName]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[text]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"eventTime\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Time]]></entry>\n			<entry name=\"name\"><![CDATA[eventTime]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[text]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"location\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Location]]></entry>\n			<entry name=\"name\"><![CDATA[location]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[text]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('32978a41-ebde-438c-930a-865d231e4784',10401,10180,10154,10158,'','2013-07-10 10:34:22','2013-07-10 10:34:22',10097,'Inventory','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Inventory</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Inventory</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"description\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"style\"><![CDATA[]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"item\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Item]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"style\"><![CDATA[]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"location\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Location]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"style\"><![CDATA[]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"purchaseDate\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Purchase Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"style\"><![CDATA[]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"double\" fieldNamespace=\"ddm\" name=\"purchasePrice\" type=\"ddm-number\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Purchase Price]]></entry>\n			<entry name=\"name\"><![CDATA[purchasePrice]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-number]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"double\" fieldNamespace=\"ddm\" name=\"quantity\" type=\"ddm-number\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Quantity]]></entry>\n			<entry name=\"name\"><![CDATA[quantity]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-number]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('edef0f2a-446b-421d-808b-0ad568fb66e6',10402,10180,10154,10158,'','2013-07-10 10:34:22','2013-07-10 10:34:22',10097,'Issues Tracking','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Issues Tracking</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Issue Tracking</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"assignedTo\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Assigned To]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"file-upload\" fieldNamespace=\"ddm\" name=\"attachment\" type=\"ddm-fileupload\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"acceptFiles\"><![CDATA[*]]></entry>\n			<entry name=\"folder\"><![CDATA[{\"folderId\":0,\"folderName\":\"Documents Home\"}]]></entry>\n			<entry name=\"label\"><![CDATA[Attachment]]></entry>\n			<entry name=\"name\"><![CDATA[attachment]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[Upload documents no larger than 3,000k.]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-fileupload]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"comments\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Comments]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"description\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"dueDate\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Due Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"issueId\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Issue ID]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"severity\" type=\"select\">\n		<dynamic-element name=\"critical\" type=\"option\" value=\"critical\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Critical]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"major\" type=\"option\" value=\"major\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Major]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"minor\" type=\"option\" value=\"minor\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Minor]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"trivial\" type=\"option\" value=\"trivial\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Trivial]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Severity]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[[\"minor\"]]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"status\" type=\"select\">\n		<dynamic-element name=\"open\" type=\"option\" value=\"open\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Open]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"pending\" type=\"option\" value=\"pending\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Pending]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"completed\" type=\"option\" value=\"completed\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Completed]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Status]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[[\"open\"]]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"title\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('4c569cf1-c035-46f8-ae5e-74dbe7591b8b',10403,10180,10154,10158,'','2013-07-10 10:34:22','2013-07-10 10:34:22',10097,'Meeting Minutes','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Meeting Minutes</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Meeting Minutes</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"file-upload\" fieldNamespace=\"ddm\" name=\"attachment\" type=\"ddm-fileupload\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"acceptFiles\"><![CDATA[*]]></entry>\n			<entry name=\"folder\"><![CDATA[{\"folderId\":0,\"folderName\":\"Documents Home\"}]]></entry>\n			<entry name=\"label\"><![CDATA[Attachment]]></entry>\n			<entry name=\"name\"><![CDATA[attachment]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[Upload documents no larger than 3,000k.]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-fileupload]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"author\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Author]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"description\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"duration\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Meeting Duration]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"meetingDate\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Meeting Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"minutes\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Minutes]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"title\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('2a384431-b6e8-4a49-8c66-a519fc9de30a',10404,10180,10154,10158,'','2013-07-10 10:34:22','2013-07-10 10:34:22',10097,'To Do','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">To Do</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">To Do</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"assignedTo\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Assigned To]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"file-upload\" fieldNamespace=\"ddm\" name=\"attachment\" type=\"ddm-fileupload\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"acceptFiles\"><![CDATA[*]]></entry>\n			<entry name=\"folder\"><![CDATA[{\"folderId\":0,\"folderName\":\"Documents Home\"}]]></entry>\n			<entry name=\"label\"><![CDATA[Attachment]]></entry>\n			<entry name=\"name\"><![CDATA[attachment]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[Upload documents no larger than 3,000k.]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-fileupload]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"comments\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Comments]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"description\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"endDate\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[End Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"integer\" fieldNamespace=\"ddm\" name=\"percentComplete\" type=\"ddm-integer\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[% Complete]]></entry>\n			<entry name=\"name\"><![CDATA[percentComplete]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[0]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-integer]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"severity\" type=\"select\">\n		<dynamic-element name=\"critical\" type=\"option\" value=\"critical\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Critical]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"major\" type=\"option\" value=\"major\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Major]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"minor\" type=\"option\" value=\"minor\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Minor]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"trivial\" type=\"option\" value=\"trivial\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Trivial]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Severity]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[[\"minor\"]]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"startDate\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Start Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"status\" type=\"select\">\n		<dynamic-element name=\"open\" type=\"option\" value=\"open\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Open]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"pending\" type=\"option\" value=\"pending\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Pending]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"completed\" type=\"option\" value=\"completed\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Completed]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Status]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[[\"open\"]]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"title\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0);
/*!40000 ALTER TABLE `DDMStructure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DDMStructureLink`
--

DROP TABLE IF EXISTS `DDMStructureLink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DDMStructureLink` (
  `structureLinkId` bigint(20) NOT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `structureId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`structureLinkId`),
  UNIQUE KEY `IX_C803899D` (`classPK`),
  KEY `IX_D43E4208` (`classNameId`),
  KEY `IX_17692B58` (`structureId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DDMStructureLink`
--

LOCK TABLES `DDMStructureLink` WRITE;
/*!40000 ALTER TABLE `DDMStructureLink` DISABLE KEYS */;
/*!40000 ALTER TABLE `DDMStructureLink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DDMTemplate`
--

DROP TABLE IF EXISTS `DDMTemplate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DDMTemplate` (
  `uuid_` varchar(75) DEFAULT NULL,
  `templateId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `structureId` bigint(20) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `type_` varchar(75) DEFAULT NULL,
  `mode_` varchar(75) DEFAULT NULL,
  `language` varchar(75) DEFAULT NULL,
  `script` longtext,
  PRIMARY KEY (`templateId`),
  UNIQUE KEY `IX_1AA75CE3` (`uuid_`,`groupId`),
  KEY `IX_DB24DDDD` (`groupId`),
  KEY `IX_33BEF579` (`language`),
  KEY `IX_C9757A51` (`structureId`),
  KEY `IX_5BC0E264` (`structureId`,`type_`),
  KEY `IX_5B019FE8` (`structureId`,`type_`,`mode_`),
  KEY `IX_C4F283C8` (`type_`),
  KEY `IX_F2A243A7` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DDMTemplate`
--

LOCK TABLES `DDMTemplate` WRITE;
/*!40000 ALTER TABLE `DDMTemplate` DISABLE KEYS */;
/*!40000 ALTER TABLE `DDMTemplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DLContent`
--

DROP TABLE IF EXISTS `DLContent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DLContent` (
  `contentId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `repositoryId` bigint(20) DEFAULT NULL,
  `path_` varchar(255) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  `data_` longblob,
  `size_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`contentId`),
  UNIQUE KEY `IX_FDD1AAA8` (`companyId`,`repositoryId`,`path_`,`version`),
  KEY `IX_6A83A66A` (`companyId`,`repositoryId`),
  KEY `IX_EB531760` (`companyId`,`repositoryId`,`path_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DLContent`
--

LOCK TABLES `DLContent` WRITE;
/*!40000 ALTER TABLE `DLContent` DISABLE KEYS */;
/*!40000 ALTER TABLE `DLContent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DLFileEntry`
--

DROP TABLE IF EXISTS `DLFileEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DLFileEntry` (
  `uuid_` varchar(75) DEFAULT NULL,
  `fileEntryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `versionUserId` bigint(20) DEFAULT NULL,
  `versionUserName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `repositoryId` bigint(20) DEFAULT NULL,
  `folderId` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `extension` varchar(75) DEFAULT NULL,
  `mimeType` varchar(75) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` longtext,
  `extraSettings` longtext,
  `fileEntryTypeId` bigint(20) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  `size_` bigint(20) DEFAULT NULL,
  `readCount` int(11) DEFAULT NULL,
  `smallImageId` bigint(20) DEFAULT NULL,
  `largeImageId` bigint(20) DEFAULT NULL,
  `custom1ImageId` bigint(20) DEFAULT NULL,
  `custom2ImageId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`fileEntryId`),
  UNIQUE KEY `IX_5391712` (`groupId`,`folderId`,`name`),
  UNIQUE KEY `IX_ED5CA615` (`groupId`,`folderId`,`title`),
  UNIQUE KEY `IX_BC2E7E6A` (`uuid_`,`groupId`),
  KEY `IX_4CB1B2B4` (`companyId`),
  KEY `IX_F4AF5636` (`groupId`),
  KEY `IX_93CF8193` (`groupId`,`folderId`),
  KEY `IX_29D0AF28` (`groupId`,`folderId`,`fileEntryTypeId`),
  KEY `IX_43261870` (`groupId`,`userId`),
  KEY `IX_D20C434D` (`groupId`,`userId`,`folderId`),
  KEY `IX_64F0FE40` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DLFileEntry`
--

LOCK TABLES `DLFileEntry` WRITE;
/*!40000 ALTER TABLE `DLFileEntry` DISABLE KEYS */;
/*!40000 ALTER TABLE `DLFileEntry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DLFileEntryMetadata`
--

DROP TABLE IF EXISTS `DLFileEntryMetadata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DLFileEntryMetadata` (
  `uuid_` varchar(75) DEFAULT NULL,
  `fileEntryMetadataId` bigint(20) NOT NULL,
  `DDMStorageId` bigint(20) DEFAULT NULL,
  `DDMStructureId` bigint(20) DEFAULT NULL,
  `fileEntryTypeId` bigint(20) DEFAULT NULL,
  `fileEntryId` bigint(20) DEFAULT NULL,
  `fileVersionId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`fileEntryMetadataId`),
  UNIQUE KEY `IX_7332B44F` (`DDMStructureId`,`fileVersionId`),
  KEY `IX_4F40FE5E` (`fileEntryId`),
  KEY `IX_A44636C9` (`fileEntryId`,`fileVersionId`),
  KEY `IX_F8E90438` (`fileEntryTypeId`),
  KEY `IX_1FE9C04` (`fileVersionId`),
  KEY `IX_D49AB5D1` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DLFileEntryMetadata`
--

LOCK TABLES `DLFileEntryMetadata` WRITE;
/*!40000 ALTER TABLE `DLFileEntryMetadata` DISABLE KEYS */;
/*!40000 ALTER TABLE `DLFileEntryMetadata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DLFileEntryType`
--

DROP TABLE IF EXISTS `DLFileEntryType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DLFileEntryType` (
  `uuid_` varchar(75) DEFAULT NULL,
  `fileEntryTypeId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`fileEntryTypeId`),
  UNIQUE KEY `IX_E9B6A85B` (`groupId`,`name`),
  UNIQUE KEY `IX_1399D844` (`uuid_`,`groupId`),
  KEY `IX_4501FD9C` (`groupId`),
  KEY `IX_90724726` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DLFileEntryType`
--

LOCK TABLES `DLFileEntryType` WRITE;
/*!40000 ALTER TABLE `DLFileEntryType` DISABLE KEYS */;
INSERT INTO `DLFileEntryType` VALUES ('57a08023-eec6-481b-aeb0-6ab4295a82d7',0,0,0,0,'','2013-07-10 10:34:07','2013-07-10 10:34:07','Basic Document',''),('3aece580-416e-4df5-bbba-06db2590445c',10300,10192,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19','Contract','Legal Contracts'),('83d20ed4-5204-46f7-a7c2-dbc5fd5b1405',10302,10192,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19','Marketing Banner','Marketing Banner'),('82eedb91-da6a-439f-b6cc-b33a031add1a',10304,10192,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19','Online Training','Online Training'),('57e2156a-3e91-4745-bd3d-432012510947',10306,10192,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19','Sales Presentation','Sales Presentation');
/*!40000 ALTER TABLE `DLFileEntryType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DLFileEntryTypes_DDMStructures`
--

DROP TABLE IF EXISTS `DLFileEntryTypes_DDMStructures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DLFileEntryTypes_DDMStructures` (
  `fileEntryTypeId` bigint(20) NOT NULL,
  `structureId` bigint(20) NOT NULL,
  PRIMARY KEY (`fileEntryTypeId`,`structureId`),
  KEY `IX_8373EC7C` (`fileEntryTypeId`),
  KEY `IX_F147CF3F` (`structureId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DLFileEntryTypes_DDMStructures`
--

LOCK TABLES `DLFileEntryTypes_DDMStructures` WRITE;
/*!40000 ALTER TABLE `DLFileEntryTypes_DDMStructures` DISABLE KEYS */;
INSERT INTO `DLFileEntryTypes_DDMStructures` VALUES (10300,10301),(10302,10298),(10302,10303),(10304,10297),(10304,10305),(10306,10299),(10306,10307);
/*!40000 ALTER TABLE `DLFileEntryTypes_DDMStructures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DLFileEntryTypes_DLFolders`
--

DROP TABLE IF EXISTS `DLFileEntryTypes_DLFolders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DLFileEntryTypes_DLFolders` (
  `fileEntryTypeId` bigint(20) NOT NULL,
  `folderId` bigint(20) NOT NULL,
  PRIMARY KEY (`fileEntryTypeId`,`folderId`),
  KEY `IX_5BB6AD6C` (`fileEntryTypeId`),
  KEY `IX_6E00A2EC` (`folderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DLFileEntryTypes_DLFolders`
--

LOCK TABLES `DLFileEntryTypes_DLFolders` WRITE;
/*!40000 ALTER TABLE `DLFileEntryTypes_DLFolders` DISABLE KEYS */;
/*!40000 ALTER TABLE `DLFileEntryTypes_DLFolders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DLFileRank`
--

DROP TABLE IF EXISTS `DLFileRank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DLFileRank` (
  `fileRankId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `fileEntryId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`fileRankId`),
  UNIQUE KEY `IX_38F0315` (`companyId`,`userId`,`fileEntryId`),
  KEY `IX_A65A1F8B` (`fileEntryId`),
  KEY `IX_BAFB116E` (`groupId`,`userId`),
  KEY `IX_EED06670` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DLFileRank`
--

LOCK TABLES `DLFileRank` WRITE;
/*!40000 ALTER TABLE `DLFileRank` DISABLE KEYS */;
/*!40000 ALTER TABLE `DLFileRank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DLFileShortcut`
--

DROP TABLE IF EXISTS `DLFileShortcut`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DLFileShortcut` (
  `uuid_` varchar(75) DEFAULT NULL,
  `fileShortcutId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `repositoryId` bigint(20) DEFAULT NULL,
  `folderId` bigint(20) DEFAULT NULL,
  `toFileEntryId` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`fileShortcutId`),
  UNIQUE KEY `IX_FDB4A946` (`uuid_`,`groupId`),
  KEY `IX_B0051937` (`groupId`,`folderId`),
  KEY `IX_ECCE311D` (`groupId`,`folderId`,`status`),
  KEY `IX_4B7247F6` (`toFileEntryId`),
  KEY `IX_4831EBE4` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DLFileShortcut`
--

LOCK TABLES `DLFileShortcut` WRITE;
/*!40000 ALTER TABLE `DLFileShortcut` DISABLE KEYS */;
/*!40000 ALTER TABLE `DLFileShortcut` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DLFileVersion`
--

DROP TABLE IF EXISTS `DLFileVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DLFileVersion` (
  `uuid_` varchar(75) DEFAULT NULL,
  `fileVersionId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `repositoryId` bigint(20) DEFAULT NULL,
  `folderId` bigint(20) DEFAULT NULL,
  `fileEntryId` bigint(20) DEFAULT NULL,
  `extension` varchar(75) DEFAULT NULL,
  `mimeType` varchar(75) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` longtext,
  `changeLog` varchar(75) DEFAULT NULL,
  `extraSettings` longtext,
  `fileEntryTypeId` bigint(20) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  `size_` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`fileVersionId`),
  UNIQUE KEY `IX_E2815081` (`fileEntryId`,`version`),
  UNIQUE KEY `IX_C99B2650` (`uuid_`,`groupId`),
  KEY `IX_C68DC967` (`fileEntryId`),
  KEY `IX_D47BB14D` (`fileEntryId`,`status`),
  KEY `IX_DFD809D3` (`groupId`,`folderId`,`status`),
  KEY `IX_9BE769ED` (`groupId`,`folderId`,`title`,`version`),
  KEY `IX_4BFABB9A` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DLFileVersion`
--

LOCK TABLES `DLFileVersion` WRITE;
/*!40000 ALTER TABLE `DLFileVersion` DISABLE KEYS */;
/*!40000 ALTER TABLE `DLFileVersion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DLFolder`
--

DROP TABLE IF EXISTS `DLFolder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DLFolder` (
  `uuid_` varchar(75) DEFAULT NULL,
  `folderId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `repositoryId` bigint(20) DEFAULT NULL,
  `mountPoint` tinyint(4) DEFAULT NULL,
  `parentFolderId` bigint(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` longtext,
  `lastPostDate` datetime DEFAULT NULL,
  `defaultFileEntryTypeId` bigint(20) DEFAULT NULL,
  `overrideFileEntryTypes` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`folderId`),
  UNIQUE KEY `IX_902FD874` (`groupId`,`parentFolderId`,`name`),
  UNIQUE KEY `IX_3CC1DED2` (`uuid_`,`groupId`),
  KEY `IX_A74DB14C` (`companyId`),
  KEY `IX_F2EA1ACE` (`groupId`),
  KEY `IX_49C37475` (`groupId`,`parentFolderId`),
  KEY `IX_2A048EA0` (`groupId`,`parentFolderId`,`mountPoint`),
  KEY `IX_51556082` (`parentFolderId`,`name`),
  KEY `IX_EE29C715` (`repositoryId`),
  KEY `IX_CBC408D8` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DLFolder`
--

LOCK TABLES `DLFolder` WRITE;
/*!40000 ALTER TABLE `DLFolder` DISABLE KEYS */;
/*!40000 ALTER TABLE `DLFolder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DLSync`
--

DROP TABLE IF EXISTS `DLSync`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DLSync` (
  `syncId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `fileId` bigint(20) DEFAULT NULL,
  `fileUuid` varchar(75) DEFAULT NULL,
  `repositoryId` bigint(20) DEFAULT NULL,
  `parentFolderId` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` longtext,
  `event` varchar(75) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`syncId`),
  UNIQUE KEY `IX_F9821AB4` (`fileId`),
  KEY `IX_B53EC783` (`companyId`,`modifiedDate`,`repositoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DLSync`
--

LOCK TABLES `DLSync` WRITE;
/*!40000 ALTER TABLE `DLSync` DISABLE KEYS */;
/*!40000 ALTER TABLE `DLSync` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EmailAddress`
--

DROP TABLE IF EXISTS `EmailAddress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EmailAddress` (
  `emailAddressId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `address` varchar(75) DEFAULT NULL,
  `typeId` int(11) DEFAULT NULL,
  `primary_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`emailAddressId`),
  KEY `IX_1BB072CA` (`companyId`),
  KEY `IX_49D2DEC4` (`companyId`,`classNameId`),
  KEY `IX_551A519F` (`companyId`,`classNameId`,`classPK`),
  KEY `IX_2A2CB130` (`companyId`,`classNameId`,`classPK`,`primary_`),
  KEY `IX_7B43CD8` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EmailAddress`
--

LOCK TABLES `EmailAddress` WRITE;
/*!40000 ALTER TABLE `EmailAddress` DISABLE KEYS */;
/*!40000 ALTER TABLE `EmailAddress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ExpandoColumn`
--

DROP TABLE IF EXISTS `ExpandoColumn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ExpandoColumn` (
  `columnId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `tableId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  `defaultData` longtext,
  `typeSettings` longtext,
  PRIMARY KEY (`columnId`),
  UNIQUE KEY `IX_FEFC8DA7` (`tableId`,`name`),
  KEY `IX_A8C0CBE8` (`tableId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ExpandoColumn`
--

LOCK TABLES `ExpandoColumn` WRITE;
/*!40000 ALTER TABLE `ExpandoColumn` DISABLE KEYS */;
/*!40000 ALTER TABLE `ExpandoColumn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ExpandoRow`
--

DROP TABLE IF EXISTS `ExpandoRow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ExpandoRow` (
  `rowId_` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `tableId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`rowId_`),
  UNIQUE KEY `IX_81EFBFF5` (`tableId`,`classPK`),
  KEY `IX_D3F5D7AE` (`tableId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ExpandoRow`
--

LOCK TABLES `ExpandoRow` WRITE;
/*!40000 ALTER TABLE `ExpandoRow` DISABLE KEYS */;
/*!40000 ALTER TABLE `ExpandoRow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ExpandoTable`
--

DROP TABLE IF EXISTS `ExpandoTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ExpandoTable` (
  `tableId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`tableId`),
  UNIQUE KEY `IX_37562284` (`companyId`,`classNameId`,`name`),
  KEY `IX_B5AE8A85` (`companyId`,`classNameId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ExpandoTable`
--

LOCK TABLES `ExpandoTable` WRITE;
/*!40000 ALTER TABLE `ExpandoTable` DISABLE KEYS */;
/*!40000 ALTER TABLE `ExpandoTable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ExpandoValue`
--

DROP TABLE IF EXISTS `ExpandoValue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ExpandoValue` (
  `valueId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `tableId` bigint(20) DEFAULT NULL,
  `columnId` bigint(20) DEFAULT NULL,
  `rowId_` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `data_` longtext,
  PRIMARY KEY (`valueId`),
  UNIQUE KEY `IX_9DDD21E5` (`columnId`,`rowId_`),
  UNIQUE KEY `IX_D27B03E7` (`tableId`,`columnId`,`classPK`),
  KEY `IX_B29FEF17` (`classNameId`,`classPK`),
  KEY `IX_F7DD0987` (`columnId`),
  KEY `IX_9112A7A0` (`rowId_`),
  KEY `IX_F0566A77` (`tableId`),
  KEY `IX_1BD3F4C` (`tableId`,`classPK`),
  KEY `IX_CA9AFB7C` (`tableId`,`columnId`),
  KEY `IX_B71E92D5` (`tableId`,`rowId_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ExpandoValue`
--

LOCK TABLES `ExpandoValue` WRITE;
/*!40000 ALTER TABLE `ExpandoValue` DISABLE KEYS */;
/*!40000 ALTER TABLE `ExpandoValue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Group_`
--

DROP TABLE IF EXISTS `Group_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Group_` (
  `groupId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `creatorUserId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `parentGroupId` bigint(20) DEFAULT NULL,
  `liveGroupId` bigint(20) DEFAULT NULL,
  `name` varchar(150) DEFAULT NULL,
  `description` longtext,
  `type_` int(11) DEFAULT NULL,
  `typeSettings` longtext,
  `friendlyURL` varchar(100) DEFAULT NULL,
  `site` tinyint(4) DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`groupId`),
  UNIQUE KEY `IX_D0D5E397` (`companyId`,`classNameId`,`classPK`),
  UNIQUE KEY `IX_5DE0BE11` (`companyId`,`classNameId`,`liveGroupId`,`name`),
  UNIQUE KEY `IX_5BDDB872` (`companyId`,`friendlyURL`),
  UNIQUE KEY `IX_BBCA55B` (`companyId`,`liveGroupId`,`name`),
  UNIQUE KEY `IX_5AA68501` (`companyId`,`name`),
  KEY `IX_ABA5CEC2` (`companyId`),
  KEY `IX_16218A38` (`liveGroupId`),
  KEY `IX_7B590A7A` (`type_`,`active_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Group_`
--

LOCK TABLES `Group_` WRITE;
/*!40000 ALTER TABLE `Group_` DISABLE KEYS */;
INSERT INTO `Group_` VALUES (10172,10154,10158,10001,10172,0,0,'Control Panel','',3,'','/control_panel',1,1),(10180,10154,10158,10001,10180,0,0,'Guest','',1,'','/guest',1,1),(10189,10154,10158,10188,10158,0,0,'User Personal Site','',3,'','/personal_site',0,1),(10192,10154,10158,10021,10154,0,0,'10154','',0,'','/null',0,1),(10198,10154,10196,10005,10196,0,0,'10196','',0,'','/test',0,1),(10310,10154,10158,10027,10309,0,0,'10309','',0,'','/template-10309',0,1),(10320,10154,10158,10027,10319,0,0,'10319','',0,'','/template-10319',0,1),(10329,10154,10158,10027,10328,0,0,'10328','',0,'','/template-10328',0,1),(10338,10154,10158,10031,10337,0,0,'10337','',0,'','/template-10337',0,1),(10364,10154,10158,10031,10363,0,0,'10363','',0,'','/template-10363',0,1);
/*!40000 ALTER TABLE `Group_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Groups_Orgs`
--

DROP TABLE IF EXISTS `Groups_Orgs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Groups_Orgs` (
  `groupId` bigint(20) NOT NULL,
  `organizationId` bigint(20) NOT NULL,
  PRIMARY KEY (`groupId`,`organizationId`),
  KEY `IX_75267DCA` (`groupId`),
  KEY `IX_6BBB7682` (`organizationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Groups_Orgs`
--

LOCK TABLES `Groups_Orgs` WRITE;
/*!40000 ALTER TABLE `Groups_Orgs` DISABLE KEYS */;
/*!40000 ALTER TABLE `Groups_Orgs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Groups_Permissions`
--

DROP TABLE IF EXISTS `Groups_Permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Groups_Permissions` (
  `groupId` bigint(20) NOT NULL,
  `permissionId` bigint(20) NOT NULL,
  PRIMARY KEY (`groupId`,`permissionId`),
  KEY `IX_C48736B` (`groupId`),
  KEY `IX_EC97689D` (`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Groups_Permissions`
--

LOCK TABLES `Groups_Permissions` WRITE;
/*!40000 ALTER TABLE `Groups_Permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `Groups_Permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Groups_Roles`
--

DROP TABLE IF EXISTS `Groups_Roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Groups_Roles` (
  `groupId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`groupId`,`roleId`),
  KEY `IX_84471FD2` (`groupId`),
  KEY `IX_3103EF3D` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Groups_Roles`
--

LOCK TABLES `Groups_Roles` WRITE;
/*!40000 ALTER TABLE `Groups_Roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `Groups_Roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Groups_UserGroups`
--

DROP TABLE IF EXISTS `Groups_UserGroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Groups_UserGroups` (
  `groupId` bigint(20) NOT NULL,
  `userGroupId` bigint(20) NOT NULL,
  PRIMARY KEY (`groupId`,`userGroupId`),
  KEY `IX_31FB749A` (`groupId`),
  KEY `IX_3B69160F` (`userGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Groups_UserGroups`
--

LOCK TABLES `Groups_UserGroups` WRITE;
/*!40000 ALTER TABLE `Groups_UserGroups` DISABLE KEYS */;
/*!40000 ALTER TABLE `Groups_UserGroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Image`
--

DROP TABLE IF EXISTS `Image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Image` (
  `imageId` bigint(20) NOT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `text_` longtext,
  `type_` varchar(75) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `size_` int(11) DEFAULT NULL,
  PRIMARY KEY (`imageId`),
  KEY `IX_6A925A4D` (`size_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Image`
--

LOCK TABLES `Image` WRITE;
/*!40000 ALTER TABLE `Image` DISABLE KEYS */;
/*!40000 ALTER TABLE `Image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JournalArticle`
--

DROP TABLE IF EXISTS `JournalArticle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JournalArticle` (
  `uuid_` varchar(75) DEFAULT NULL,
  `id_` bigint(20) NOT NULL,
  `resourcePrimKey` bigint(20) DEFAULT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `articleId` varchar(75) DEFAULT NULL,
  `version` double DEFAULT NULL,
  `title` longtext,
  `urlTitle` varchar(150) DEFAULT NULL,
  `description` longtext,
  `content` longtext,
  `type_` varchar(75) DEFAULT NULL,
  `structureId` varchar(75) DEFAULT NULL,
  `templateId` varchar(75) DEFAULT NULL,
  `layoutUuid` varchar(75) DEFAULT NULL,
  `displayDate` datetime DEFAULT NULL,
  `expirationDate` datetime DEFAULT NULL,
  `reviewDate` datetime DEFAULT NULL,
  `indexable` tinyint(4) DEFAULT NULL,
  `smallImage` tinyint(4) DEFAULT NULL,
  `smallImageId` bigint(20) DEFAULT NULL,
  `smallImageURL` longtext,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `IX_85C52EEC` (`groupId`,`articleId`,`version`),
  UNIQUE KEY `IX_3463D95B` (`uuid_`,`groupId`),
  KEY `IX_DFF98523` (`companyId`),
  KEY `IX_323DF109` (`companyId`,`status`),
  KEY `IX_3D070845` (`companyId`,`version`),
  KEY `IX_E82F322B` (`companyId`,`version`,`status`),
  KEY `IX_9356F865` (`groupId`),
  KEY `IX_68C0F69C` (`groupId`,`articleId`),
  KEY `IX_4D5CD982` (`groupId`,`articleId`,`status`),
  KEY `IX_9CE6E0FA` (`groupId`,`classNameId`,`classPK`),
  KEY `IX_A2534AC2` (`groupId`,`classNameId`,`layoutUuid`),
  KEY `IX_91E78C35` (`groupId`,`classNameId`,`structureId`),
  KEY `IX_F43B9FF2` (`groupId`,`classNameId`,`templateId`),
  KEY `IX_3C028C1E` (`groupId`,`layoutUuid`),
  KEY `IX_301D024B` (`groupId`,`status`),
  KEY `IX_2E207659` (`groupId`,`structureId`),
  KEY `IX_8DEAE14E` (`groupId`,`templateId`),
  KEY `IX_22882D02` (`groupId`,`urlTitle`),
  KEY `IX_D2D249E8` (`groupId`,`urlTitle`,`status`),
  KEY `IX_F0A26B29` (`groupId`,`version`,`status`),
  KEY `IX_33F49D16` (`resourcePrimKey`),
  KEY `IX_3E2765FC` (`resourcePrimKey`,`status`),
  KEY `IX_EF9B7028` (`smallImageId`),
  KEY `IX_8E8710D9` (`structureId`),
  KEY `IX_9106F6CE` (`templateId`),
  KEY `IX_F029602F` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JournalArticle`
--

LOCK TABLES `JournalArticle` WRITE;
/*!40000 ALTER TABLE `JournalArticle` DISABLE KEYS */;
/*!40000 ALTER TABLE `JournalArticle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JournalArticleImage`
--

DROP TABLE IF EXISTS `JournalArticleImage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JournalArticleImage` (
  `articleImageId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `articleId` varchar(75) DEFAULT NULL,
  `version` double DEFAULT NULL,
  `elInstanceId` varchar(75) DEFAULT NULL,
  `elName` varchar(75) DEFAULT NULL,
  `languageId` varchar(75) DEFAULT NULL,
  `tempImage` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`articleImageId`),
  UNIQUE KEY `IX_103D6207` (`groupId`,`articleId`,`version`,`elInstanceId`,`elName`,`languageId`),
  KEY `IX_3B51BB68` (`groupId`),
  KEY `IX_158B526F` (`groupId`,`articleId`,`version`),
  KEY `IX_D4121315` (`tempImage`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JournalArticleImage`
--

LOCK TABLES `JournalArticleImage` WRITE;
/*!40000 ALTER TABLE `JournalArticleImage` DISABLE KEYS */;
/*!40000 ALTER TABLE `JournalArticleImage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JournalArticleResource`
--

DROP TABLE IF EXISTS `JournalArticleResource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JournalArticleResource` (
  `uuid_` varchar(75) DEFAULT NULL,
  `resourcePrimKey` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `articleId` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`resourcePrimKey`),
  UNIQUE KEY `IX_88DF994A` (`groupId`,`articleId`),
  UNIQUE KEY `IX_84AB0309` (`uuid_`,`groupId`),
  KEY `IX_F8433677` (`groupId`),
  KEY `IX_DCD1FAC1` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JournalArticleResource`
--

LOCK TABLES `JournalArticleResource` WRITE;
/*!40000 ALTER TABLE `JournalArticleResource` DISABLE KEYS */;
/*!40000 ALTER TABLE `JournalArticleResource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JournalContentSearch`
--

DROP TABLE IF EXISTS `JournalContentSearch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JournalContentSearch` (
  `contentSearchId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `privateLayout` tinyint(4) DEFAULT NULL,
  `layoutId` bigint(20) DEFAULT NULL,
  `portletId` varchar(200) DEFAULT NULL,
  `articleId` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`contentSearchId`),
  UNIQUE KEY `IX_C3AA93B8` (`groupId`,`privateLayout`,`layoutId`,`portletId`,`articleId`),
  KEY `IX_9207CB31` (`articleId`),
  KEY `IX_6838E427` (`groupId`,`articleId`),
  KEY `IX_20962903` (`groupId`,`privateLayout`),
  KEY `IX_7CC7D73E` (`groupId`,`privateLayout`,`articleId`),
  KEY `IX_B3B318DC` (`groupId`,`privateLayout`,`layoutId`),
  KEY `IX_7ACC74C9` (`groupId`,`privateLayout`,`layoutId`,`portletId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JournalContentSearch`
--

LOCK TABLES `JournalContentSearch` WRITE;
/*!40000 ALTER TABLE `JournalContentSearch` DISABLE KEYS */;
/*!40000 ALTER TABLE `JournalContentSearch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JournalFeed`
--

DROP TABLE IF EXISTS `JournalFeed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JournalFeed` (
  `uuid_` varchar(75) DEFAULT NULL,
  `id_` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `feedId` varchar(75) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `type_` varchar(75) DEFAULT NULL,
  `structureId` varchar(75) DEFAULT NULL,
  `templateId` varchar(75) DEFAULT NULL,
  `rendererTemplateId` varchar(75) DEFAULT NULL,
  `delta` int(11) DEFAULT NULL,
  `orderByCol` varchar(75) DEFAULT NULL,
  `orderByType` varchar(75) DEFAULT NULL,
  `targetLayoutFriendlyUrl` varchar(255) DEFAULT NULL,
  `targetPortletId` varchar(75) DEFAULT NULL,
  `contentField` varchar(75) DEFAULT NULL,
  `feedType` varchar(75) DEFAULT NULL,
  `feedVersion` double DEFAULT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `IX_65576CBC` (`groupId`,`feedId`),
  UNIQUE KEY `IX_39031F51` (`uuid_`,`groupId`),
  KEY `IX_35A2DB2F` (`groupId`),
  KEY `IX_50C36D79` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JournalFeed`
--

LOCK TABLES `JournalFeed` WRITE;
/*!40000 ALTER TABLE `JournalFeed` DISABLE KEYS */;
/*!40000 ALTER TABLE `JournalFeed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JournalStructure`
--

DROP TABLE IF EXISTS `JournalStructure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JournalStructure` (
  `uuid_` varchar(75) DEFAULT NULL,
  `id_` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `structureId` varchar(75) DEFAULT NULL,
  `parentStructureId` varchar(75) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `xsd` longtext,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `IX_AB6E9996` (`groupId`,`structureId`),
  UNIQUE KEY `IX_42E86E58` (`uuid_`,`groupId`),
  KEY `IX_B97F5608` (`groupId`),
  KEY `IX_CA0BD48C` (`groupId`,`parentStructureId`),
  KEY `IX_4FA67B72` (`parentStructureId`),
  KEY `IX_8831E4FC` (`structureId`),
  KEY `IX_6702CA92` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JournalStructure`
--

LOCK TABLES `JournalStructure` WRITE;
/*!40000 ALTER TABLE `JournalStructure` DISABLE KEYS */;
/*!40000 ALTER TABLE `JournalStructure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `JournalTemplate`
--

DROP TABLE IF EXISTS `JournalTemplate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JournalTemplate` (
  `uuid_` varchar(75) DEFAULT NULL,
  `id_` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `templateId` varchar(75) DEFAULT NULL,
  `structureId` varchar(75) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `xsl` longtext,
  `langType` varchar(75) DEFAULT NULL,
  `cacheable` tinyint(4) DEFAULT NULL,
  `smallImage` tinyint(4) DEFAULT NULL,
  `smallImageId` bigint(20) DEFAULT NULL,
  `smallImageURL` longtext,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `IX_E802AA3C` (`groupId`,`templateId`),
  UNIQUE KEY `IX_62D1B3AD` (`uuid_`,`groupId`),
  KEY `IX_77923653` (`groupId`),
  KEY `IX_1701CB2B` (`groupId`,`structureId`),
  KEY `IX_25FFB6FA` (`smallImageId`),
  KEY `IX_45F5A7C7` (`structureId`),
  KEY `IX_1B12CA20` (`templateId`),
  KEY `IX_2857419D` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JournalTemplate`
--

LOCK TABLES `JournalTemplate` WRITE;
/*!40000 ALTER TABLE `JournalTemplate` DISABLE KEYS */;
/*!40000 ALTER TABLE `JournalTemplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Layout`
--

DROP TABLE IF EXISTS `Layout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Layout` (
  `uuid_` varchar(75) DEFAULT NULL,
  `plid` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `privateLayout` tinyint(4) DEFAULT NULL,
  `layoutId` bigint(20) DEFAULT NULL,
  `parentLayoutId` bigint(20) DEFAULT NULL,
  `name` longtext,
  `title` longtext,
  `description` longtext,
  `keywords` longtext,
  `robots` longtext,
  `type_` varchar(75) DEFAULT NULL,
  `typeSettings` longtext,
  `hidden_` tinyint(4) DEFAULT NULL,
  `friendlyURL` varchar(255) DEFAULT NULL,
  `iconImage` tinyint(4) DEFAULT NULL,
  `iconImageId` bigint(20) DEFAULT NULL,
  `themeId` varchar(75) DEFAULT NULL,
  `colorSchemeId` varchar(75) DEFAULT NULL,
  `wapThemeId` varchar(75) DEFAULT NULL,
  `wapColorSchemeId` varchar(75) DEFAULT NULL,
  `css` longtext,
  `priority` int(11) DEFAULT NULL,
  `layoutPrototypeUuid` varchar(75) DEFAULT NULL,
  `layoutPrototypeLinkEnabled` tinyint(4) DEFAULT NULL,
  `sourcePrototypeLayoutUuid` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`plid`),
  UNIQUE KEY `IX_BC2C4231` (`groupId`,`privateLayout`,`friendlyURL`),
  UNIQUE KEY `IX_7162C27C` (`groupId`,`privateLayout`,`layoutId`),
  UNIQUE KEY `IX_CED31606` (`uuid_`,`groupId`),
  KEY `IX_C7FBC998` (`companyId`),
  KEY `IX_C099D61A` (`groupId`),
  KEY `IX_705F5AA3` (`groupId`,`privateLayout`),
  KEY `IX_6DE88B06` (`groupId`,`privateLayout`,`parentLayoutId`),
  KEY `IX_8CE8C0D9` (`groupId`,`privateLayout`,`sourcePrototypeLayoutUuid`),
  KEY `IX_1A1B61D2` (`groupId`,`privateLayout`,`type_`),
  KEY `IX_23922F7D` (`iconImageId`),
  KEY `IX_B529BFD3` (`layoutPrototypeUuid`),
  KEY `IX_D0822724` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Layout`
--

LOCK TABLES `Layout` WRITE;
/*!40000 ALTER TABLE `Layout` DISABLE KEYS */;
INSERT INTO `Layout` VALUES ('e74b8ba4-7035-4a76-a3fa-1182d6d63aab',10175,10172,10154,'2013-07-10 10:34:14','2013-07-10 10:34:14',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Control Panel</Name></root>','','','','','control_panel','',0,'/manage',0,0,'','','','','',0,'',0,''),('9faa867c-7ce3-44b3-9c65-a36ad0aa845f',10183,10180,10154,'2013-07-10 10:34:14','2013-07-10 10:34:14',0,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Welcome</Name></root>','','','','','portlet','layout-template-id=2_columns_ii\ncolumn-2=47,\ncolumn-1=58,\n',0,'/home',0,0,'','','','','',0,'',0,''),('b5aa02fd-96b0-48bf-a743-ce7fa98095db',10313,10310,10154,'2013-07-10 10:34:19','2013-07-10 10:34:19',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Blog</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=148_INSTANCE_25GFqsluNMC2,114,\ncolumn-1=33,\n',0,'/layout',0,0,'','','','','',0,'',0,''),('82d89872-aa9b-456c-a2ca-c1fe8c1d31c1',10323,10320,10154,'2013-07-10 10:34:20','2013-07-10 10:34:20',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Content Display Page</Name></root>','','','','','portlet','default-asset-publisher-portlet-id=101_INSTANCE_1UT6mxxw3qAs\nlayout-template-id=2_columns_ii\ncolumn-2=3,101_INSTANCE_1UT6mxxw3qAs,\ncolumn-1=141_INSTANCE_fWpN7YyLYuRA,122_INSTANCE_qqTARFxOfv8B,\n',0,'/layout',0,0,'','','','','',0,'',0,''),('a4ec8ca8-e661-47bd-ad9f-53c91b9b9025',10332,10329,10154,'2013-07-10 10:34:20','2013-07-10 10:34:20',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Wiki</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=122_INSTANCE_vwqJJu9YUePo,141_INSTANCE_B0Ccx2TXMdw7,\ncolumn-1=36,\n',0,'/layout',0,0,'','','','','',0,'',0,''),('0630c597-f0e7-4317-b630-f2e80cb919bf',10346,10338,10154,'2013-07-10 10:34:20','2013-07-10 10:34:20',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Home</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=3,59_INSTANCE_usCfSDxTaBS4,180,\ncolumn-1=19,\n',0,'/home',0,0,'','','','','',0,'',0,''),('ef32f137-1b97-4f80-9a04-2babe795786b',10352,10338,10154,'2013-07-10 10:34:20','2013-07-10 10:34:21',1,2,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Calendar</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=101_INSTANCE_NACG9Qd7cKf1,\ncolumn-1=8,\n',0,'/calendar',0,0,'','','','','',1,'',0,''),('f703d4d7-5b90-4b0d-b906-fe76ec080675',10358,10338,10154,'2013-07-10 10:34:21','2013-07-10 10:34:21',1,3,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Wiki</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=122_INSTANCE_ioO01Fzgw5lC,148_INSTANCE_Asu8VkeE4a23,\ncolumn-1=36,\n',0,'/wiki',0,0,'','','','','',2,'',0,''),('3a1b2dc2-d821-4ba8-a8f3-e76fe4cd8c51',10372,10364,10154,'2013-07-10 10:34:21','2013-07-10 10:34:21',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Home</Name></root>','','','','','portlet','layout-template-id=2_columns_i\ncolumn-2=3,82,101_INSTANCE_Svf0QgfbEiVg,\ncolumn-1=116,\n',0,'/home',0,0,'','','','','',0,'',0,''),('a5698cac-e45b-4de5-912b-49d60aee2c61',10380,10364,10154,'2013-07-10 10:34:21','2013-07-10 10:34:21',1,2,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Documents and Media</Name></root>','','','','','portlet','layout-template-id=1_column\ncolumn-1=20,\n',0,'/documents',0,0,'','','','','',1,'',0,''),('a806459c-1e3e-40ee-8d2b-cbead6fa00bf',10386,10364,10154,'2013-07-10 10:34:21','2013-07-10 10:34:21',1,3,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Calendar</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=101_INSTANCE_yA8x8bByfliv,\ncolumn-1=8,\n',0,'/calendar',0,0,'','','','','',2,'',0,''),('c894948e-2fe5-40e1-a7ff-b36e445cedbe',10392,10364,10154,'2013-07-10 10:34:21','2013-07-10 10:34:21',1,4,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">News</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=39_INSTANCE_7KqnBsHGzom2,\ncolumn-1=39_INSTANCE_BLhgNLhU4elv,\n',0,'/news',0,0,'','','','','',3,'',0,'');
/*!40000 ALTER TABLE `Layout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LayoutBranch`
--

DROP TABLE IF EXISTS `LayoutBranch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LayoutBranch` (
  `LayoutBranchId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `layoutSetBranchId` bigint(20) DEFAULT NULL,
  `plid` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `master` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`LayoutBranchId`),
  UNIQUE KEY `IX_FD57097D` (`layoutSetBranchId`,`plid`,`name`),
  KEY `IX_6C226433` (`layoutSetBranchId`),
  KEY `IX_2C42603E` (`layoutSetBranchId`,`plid`),
  KEY `IX_A705FF94` (`layoutSetBranchId`,`plid`,`master`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LayoutBranch`
--

LOCK TABLES `LayoutBranch` WRITE;
/*!40000 ALTER TABLE `LayoutBranch` DISABLE KEYS */;
/*!40000 ALTER TABLE `LayoutBranch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LayoutPrototype`
--

DROP TABLE IF EXISTS `LayoutPrototype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LayoutPrototype` (
  `uuid_` varchar(75) DEFAULT NULL,
  `layoutPrototypeId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `settings_` longtext,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`layoutPrototypeId`),
  KEY `IX_30616AAA` (`companyId`),
  KEY `IX_557A639F` (`companyId`,`active_`),
  KEY `IX_CEF72136` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LayoutPrototype`
--

LOCK TABLES `LayoutPrototype` WRITE;
/*!40000 ALTER TABLE `LayoutPrototype` DISABLE KEYS */;
INSERT INTO `LayoutPrototype` VALUES ('ba6d02f6-0987-4e44-bcfa-e75e6ca2adae',10309,10154,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Blog</Name></root>','Create, edit, and view blogs from this page. Explore topics using tags, and connect with other members that blog.','',1),('833ec902-14a8-47a2-a63a-a94b393b0fd7',10319,10154,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Content Display Page</Name></root>','Create, edit, and explore web content with this page. Search available content, explore related content with tags, and browse content categories.','',1),('ca7a2f8f-4ff5-429a-b752-3bc4b8d327ef',10328,10154,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Wiki</Name></root>','Collaborate with members through the wiki on this page. Discover related content through tags, and navigate quickly and easily with categories.','',1);
/*!40000 ALTER TABLE `LayoutPrototype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LayoutRevision`
--

DROP TABLE IF EXISTS `LayoutRevision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LayoutRevision` (
  `layoutRevisionId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `layoutSetBranchId` bigint(20) DEFAULT NULL,
  `layoutBranchId` bigint(20) DEFAULT NULL,
  `parentLayoutRevisionId` bigint(20) DEFAULT NULL,
  `head` tinyint(4) DEFAULT NULL,
  `major` tinyint(4) DEFAULT NULL,
  `plid` bigint(20) DEFAULT NULL,
  `privateLayout` tinyint(4) DEFAULT NULL,
  `name` longtext,
  `title` longtext,
  `description` longtext,
  `keywords` longtext,
  `robots` longtext,
  `typeSettings` longtext,
  `iconImage` tinyint(4) DEFAULT NULL,
  `iconImageId` bigint(20) DEFAULT NULL,
  `themeId` varchar(75) DEFAULT NULL,
  `colorSchemeId` varchar(75) DEFAULT NULL,
  `wapThemeId` varchar(75) DEFAULT NULL,
  `wapColorSchemeId` varchar(75) DEFAULT NULL,
  `css` longtext,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`layoutRevisionId`),
  KEY `IX_43E8286A` (`head`,`plid`),
  KEY `IX_314B621A` (`layoutSetBranchId`),
  KEY `IX_A9AC086E` (`layoutSetBranchId`,`head`),
  KEY `IX_E10AC39` (`layoutSetBranchId`,`head`,`plid`),
  KEY `IX_13984800` (`layoutSetBranchId`,`layoutBranchId`,`plid`),
  KEY `IX_4A84AF43` (`layoutSetBranchId`,`parentLayoutRevisionId`,`plid`),
  KEY `IX_B7B914E5` (`layoutSetBranchId`,`plid`),
  KEY `IX_70DA9ECB` (`layoutSetBranchId`,`plid`,`status`),
  KEY `IX_7FFAE700` (`layoutSetBranchId`,`status`),
  KEY `IX_9329C9D6` (`plid`),
  KEY `IX_8EC3D2BC` (`plid`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LayoutRevision`
--

LOCK TABLES `LayoutRevision` WRITE;
/*!40000 ALTER TABLE `LayoutRevision` DISABLE KEYS */;
/*!40000 ALTER TABLE `LayoutRevision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LayoutSet`
--

DROP TABLE IF EXISTS `LayoutSet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LayoutSet` (
  `layoutSetId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `privateLayout` tinyint(4) DEFAULT NULL,
  `logo` tinyint(4) DEFAULT NULL,
  `logoId` bigint(20) DEFAULT NULL,
  `themeId` varchar(75) DEFAULT NULL,
  `colorSchemeId` varchar(75) DEFAULT NULL,
  `wapThemeId` varchar(75) DEFAULT NULL,
  `wapColorSchemeId` varchar(75) DEFAULT NULL,
  `css` longtext,
  `pageCount` int(11) DEFAULT NULL,
  `settings_` longtext,
  `layoutSetPrototypeUuid` varchar(75) DEFAULT NULL,
  `layoutSetPrototypeLinkEnabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`layoutSetId`),
  UNIQUE KEY `IX_48550691` (`groupId`,`privateLayout`),
  KEY `IX_A40B8BEC` (`groupId`),
  KEY `IX_72BBA8B7` (`layoutSetPrototypeUuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LayoutSet`
--

LOCK TABLES `LayoutSet` WRITE;
/*!40000 ALTER TABLE `LayoutSet` DISABLE KEYS */;
INSERT INTO `LayoutSet` VALUES (10173,10172,10154,'2013-07-10 10:34:14','2013-07-10 10:34:14',1,0,0,'classic','01','mobile','01','',1,'','',0),(10174,10172,10154,'2013-07-10 10:34:14','2013-07-10 10:34:14',0,0,0,'classic','01','mobile','01','',0,'','',0),(10181,10180,10154,'2013-07-10 10:34:14','2013-07-10 10:34:14',1,0,0,'classic','01','mobile','01','',0,'','',0),(10182,10180,10154,'2013-07-10 10:34:14','2013-07-10 10:34:14',0,0,0,'classic','01','mobile','01','',1,'','',0),(10190,10189,10154,'2013-07-10 10:34:14','2013-07-10 10:34:14',1,0,0,'classic','01','mobile','01','',0,'','',0),(10191,10189,10154,'2013-07-10 10:34:14','2013-07-10 10:34:14',0,0,0,'classic','01','mobile','01','',0,'','',0),(10193,10192,10154,'2013-07-10 10:34:15','2013-07-10 10:34:15',1,0,0,'classic','01','mobile','01','',0,'','',0),(10194,10192,10154,'2013-07-10 10:34:15','2013-07-10 10:34:15',0,0,0,'classic','01','mobile','01','',0,'','',0),(10199,10198,10154,'2013-07-10 10:34:15','2013-07-10 10:34:15',1,0,0,'classic','01','mobile','01','',0,'','',0),(10200,10198,10154,'2013-07-10 10:34:15','2013-07-10 10:34:15',0,0,0,'classic','01','mobile','01','',0,'','',0),(10311,10310,10154,'2013-07-10 10:34:19','2013-07-10 10:34:19',1,0,0,'classic','01','mobile','01','',1,'','',0),(10312,10310,10154,'2013-07-10 10:34:19','2013-07-10 10:34:19',0,0,0,'classic','01','mobile','01','',0,'','',0),(10321,10320,10154,'2013-07-10 10:34:20','2013-07-10 10:34:20',1,0,0,'classic','01','mobile','01','',1,'','',0),(10322,10320,10154,'2013-07-10 10:34:20','2013-07-10 10:34:20',0,0,0,'classic','01','mobile','01','',0,'','',0),(10330,10329,10154,'2013-07-10 10:34:20','2013-07-10 10:34:20',1,0,0,'classic','01','mobile','01','',1,'','',0),(10331,10329,10154,'2013-07-10 10:34:20','2013-07-10 10:34:20',0,0,0,'classic','01','mobile','01','',0,'','',0),(10339,10338,10154,'2013-07-10 10:34:20','2013-07-10 10:34:21',1,0,0,'classic','01','mobile','01','',3,'','',0),(10340,10338,10154,'2013-07-10 10:34:20','2013-07-10 10:34:20',0,0,0,'classic','01','mobile','01','',0,'','',0),(10365,10364,10154,'2013-07-10 10:34:21','2013-07-10 10:34:21',1,0,0,'classic','01','mobile','01','',4,'','',0),(10366,10364,10154,'2013-07-10 10:34:21','2013-07-10 10:34:21',0,0,0,'classic','01','mobile','01','',0,'','',0);
/*!40000 ALTER TABLE `LayoutSet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LayoutSetBranch`
--

DROP TABLE IF EXISTS `LayoutSetBranch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LayoutSetBranch` (
  `layoutSetBranchId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `privateLayout` tinyint(4) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `master` tinyint(4) DEFAULT NULL,
  `logo` tinyint(4) DEFAULT NULL,
  `logoId` bigint(20) DEFAULT NULL,
  `themeId` varchar(75) DEFAULT NULL,
  `colorSchemeId` varchar(75) DEFAULT NULL,
  `wapThemeId` varchar(75) DEFAULT NULL,
  `wapColorSchemeId` varchar(75) DEFAULT NULL,
  `css` longtext,
  `settings_` longtext,
  `layoutSetPrototypeUuid` varchar(75) DEFAULT NULL,
  `layoutSetPrototypeLinkEnabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`layoutSetBranchId`),
  UNIQUE KEY `IX_5FF18552` (`groupId`,`privateLayout`,`name`),
  KEY `IX_8FF5D6EA` (`groupId`),
  KEY `IX_C4079FD3` (`groupId`,`privateLayout`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LayoutSetBranch`
--

LOCK TABLES `LayoutSetBranch` WRITE;
/*!40000 ALTER TABLE `LayoutSetBranch` DISABLE KEYS */;
/*!40000 ALTER TABLE `LayoutSetBranch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LayoutSetPrototype`
--

DROP TABLE IF EXISTS `LayoutSetPrototype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LayoutSetPrototype` (
  `uuid_` varchar(75) DEFAULT NULL,
  `layoutSetPrototypeId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `settings_` longtext,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`layoutSetPrototypeId`),
  KEY `IX_55F63D98` (`companyId`),
  KEY `IX_9178FC71` (`companyId`,`active_`),
  KEY `IX_C5D69B24` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LayoutSetPrototype`
--

LOCK TABLES `LayoutSetPrototype` WRITE;
/*!40000 ALTER TABLE `LayoutSetPrototype` DISABLE KEYS */;
INSERT INTO `LayoutSetPrototype` VALUES ('bac2b65c-3a09-4ae4-8d98-e8bff1a47e16',10337,10154,'2013-07-10 10:34:20','2013-07-10 10:34:21','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Community Site</Name></root>','Site with Forums, Calendar and Wiki','layoutsUpdateable=true\n',1),('bba65b4a-9298-4ecf-91ec-bfbd98a90121',10363,10154,'2013-07-10 10:34:21','2013-07-10 10:34:21','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Intranet Site</Name></root>','Site with Documents, Calendar and News','layoutsUpdateable=true\n',1);
/*!40000 ALTER TABLE `LayoutSetPrototype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ListType`
--

DROP TABLE IF EXISTS `ListType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ListType` (
  `listTypeId` int(11) NOT NULL,
  `name` varchar(75) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`listTypeId`),
  KEY `IX_2932DD37` (`type_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ListType`
--

LOCK TABLES `ListType` WRITE;
/*!40000 ALTER TABLE `ListType` DISABLE KEYS */;
INSERT INTO `ListType` VALUES (10000,'billing','com.liferay.portal.model.Account.address'),(10001,'other','com.liferay.portal.model.Account.address'),(10002,'p-o-box','com.liferay.portal.model.Account.address'),(10003,'shipping','com.liferay.portal.model.Account.address'),(10004,'email-address','com.liferay.portal.model.Account.emailAddress'),(10005,'email-address-2','com.liferay.portal.model.Account.emailAddress'),(10006,'email-address-3','com.liferay.portal.model.Account.emailAddress'),(10007,'fax','com.liferay.portal.model.Account.phone'),(10008,'local','com.liferay.portal.model.Account.phone'),(10009,'other','com.liferay.portal.model.Account.phone'),(10010,'toll-free','com.liferay.portal.model.Account.phone'),(10011,'tty','com.liferay.portal.model.Account.phone'),(10012,'intranet','com.liferay.portal.model.Account.website'),(10013,'public','com.liferay.portal.model.Account.website'),(11000,'business','com.liferay.portal.model.Contact.address'),(11001,'other','com.liferay.portal.model.Contact.address'),(11002,'personal','com.liferay.portal.model.Contact.address'),(11003,'email-address','com.liferay.portal.model.Contact.emailAddress'),(11004,'email-address-2','com.liferay.portal.model.Contact.emailAddress'),(11005,'email-address-3','com.liferay.portal.model.Contact.emailAddress'),(11006,'business','com.liferay.portal.model.Contact.phone'),(11007,'business-fax','com.liferay.portal.model.Contact.phone'),(11008,'mobile-phone','com.liferay.portal.model.Contact.phone'),(11009,'other','com.liferay.portal.model.Contact.phone'),(11010,'pager','com.liferay.portal.model.Contact.phone'),(11011,'personal','com.liferay.portal.model.Contact.phone'),(11012,'personal-fax','com.liferay.portal.model.Contact.phone'),(11013,'tty','com.liferay.portal.model.Contact.phone'),(11014,'dr','com.liferay.portal.model.Contact.prefix'),(11015,'mr','com.liferay.portal.model.Contact.prefix'),(11016,'mrs','com.liferay.portal.model.Contact.prefix'),(11017,'ms','com.liferay.portal.model.Contact.prefix'),(11020,'ii','com.liferay.portal.model.Contact.suffix'),(11021,'iii','com.liferay.portal.model.Contact.suffix'),(11022,'iv','com.liferay.portal.model.Contact.suffix'),(11023,'jr','com.liferay.portal.model.Contact.suffix'),(11024,'phd','com.liferay.portal.model.Contact.suffix'),(11025,'sr','com.liferay.portal.model.Contact.suffix'),(11026,'blog','com.liferay.portal.model.Contact.website'),(11027,'business','com.liferay.portal.model.Contact.website'),(11028,'other','com.liferay.portal.model.Contact.website'),(11029,'personal','com.liferay.portal.model.Contact.website'),(12000,'billing','com.liferay.portal.model.Organization.address'),(12001,'other','com.liferay.portal.model.Organization.address'),(12002,'p-o-box','com.liferay.portal.model.Organization.address'),(12003,'shipping','com.liferay.portal.model.Organization.address'),(12004,'email-address','com.liferay.portal.model.Organization.emailAddress'),(12005,'email-address-2','com.liferay.portal.model.Organization.emailAddress'),(12006,'email-address-3','com.liferay.portal.model.Organization.emailAddress'),(12007,'fax','com.liferay.portal.model.Organization.phone'),(12008,'local','com.liferay.portal.model.Organization.phone'),(12009,'other','com.liferay.portal.model.Organization.phone'),(12010,'toll-free','com.liferay.portal.model.Organization.phone'),(12011,'tty','com.liferay.portal.model.Organization.phone'),(12012,'administrative','com.liferay.portal.model.Organization.service'),(12013,'contracts','com.liferay.portal.model.Organization.service'),(12014,'donation','com.liferay.portal.model.Organization.service'),(12015,'retail','com.liferay.portal.model.Organization.service'),(12016,'training','com.liferay.portal.model.Organization.service'),(12017,'full-member','com.liferay.portal.model.Organization.status'),(12018,'provisional-member','com.liferay.portal.model.Organization.status'),(12019,'intranet','com.liferay.portal.model.Organization.website'),(12020,'public','com.liferay.portal.model.Organization.website');
/*!40000 ALTER TABLE `ListType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Lock_`
--

DROP TABLE IF EXISTS `Lock_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Lock_` (
  `uuid_` varchar(75) DEFAULT NULL,
  `lockId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `className` varchar(75) DEFAULT NULL,
  `key_` varchar(200) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `inheritable` tinyint(4) DEFAULT NULL,
  `expirationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`lockId`),
  UNIQUE KEY `IX_DD635956` (`className`,`key_`,`owner`),
  KEY `IX_228562AD` (`className`,`key_`),
  KEY `IX_E3F1286B` (`expirationDate`),
  KEY `IX_13C5CD3A` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Lock_`
--

LOCK TABLES `Lock_` WRITE;
/*!40000 ALTER TABLE `Lock_` DISABLE KEYS */;
/*!40000 ALTER TABLE `Lock_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MBBan`
--

DROP TABLE IF EXISTS `MBBan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MBBan` (
  `banId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `banUserId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`banId`),
  UNIQUE KEY `IX_8ABC4E3B` (`groupId`,`banUserId`),
  KEY `IX_69951A25` (`banUserId`),
  KEY `IX_5C3FF12A` (`groupId`),
  KEY `IX_48814BBA` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MBBan`
--

LOCK TABLES `MBBan` WRITE;
/*!40000 ALTER TABLE `MBBan` DISABLE KEYS */;
/*!40000 ALTER TABLE `MBBan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MBCategory`
--

DROP TABLE IF EXISTS `MBCategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MBCategory` (
  `uuid_` varchar(75) DEFAULT NULL,
  `categoryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `parentCategoryId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `displayStyle` varchar(75) DEFAULT NULL,
  `threadCount` int(11) DEFAULT NULL,
  `messageCount` int(11) DEFAULT NULL,
  `lastPostDate` datetime DEFAULT NULL,
  PRIMARY KEY (`categoryId`),
  UNIQUE KEY `IX_F7D28C2F` (`uuid_`,`groupId`),
  KEY `IX_BC735DCF` (`companyId`),
  KEY `IX_BB870C11` (`groupId`),
  KEY `IX_ED292508` (`groupId`,`parentCategoryId`),
  KEY `IX_C2626EDB` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MBCategory`
--

LOCK TABLES `MBCategory` WRITE;
/*!40000 ALTER TABLE `MBCategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `MBCategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MBDiscussion`
--

DROP TABLE IF EXISTS `MBDiscussion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MBDiscussion` (
  `discussionId` bigint(20) NOT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `threadId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`discussionId`),
  UNIQUE KEY `IX_33A4DE38` (`classNameId`,`classPK`),
  UNIQUE KEY `IX_B5CA2DC` (`threadId`),
  KEY `IX_79D0120B` (`classNameId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MBDiscussion`
--

LOCK TABLES `MBDiscussion` WRITE;
/*!40000 ALTER TABLE `MBDiscussion` DISABLE KEYS */;
INSERT INTO `MBDiscussion` VALUES (10179,10002,10175,10177),(10187,10002,10183,10185),(10318,10002,10313,10315),(10327,10002,10323,10325),(10336,10002,10332,10334),(10350,10002,10346,10348),(10356,10002,10352,10354),(10362,10002,10358,10360),(10376,10002,10372,10374),(10384,10002,10380,10382),(10390,10002,10386,10388),(10396,10002,10392,10394);
/*!40000 ALTER TABLE `MBDiscussion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MBMailingList`
--

DROP TABLE IF EXISTS `MBMailingList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MBMailingList` (
  `uuid_` varchar(75) DEFAULT NULL,
  `mailingListId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `categoryId` bigint(20) DEFAULT NULL,
  `emailAddress` varchar(75) DEFAULT NULL,
  `inProtocol` varchar(75) DEFAULT NULL,
  `inServerName` varchar(75) DEFAULT NULL,
  `inServerPort` int(11) DEFAULT NULL,
  `inUseSSL` tinyint(4) DEFAULT NULL,
  `inUserName` varchar(75) DEFAULT NULL,
  `inPassword` varchar(75) DEFAULT NULL,
  `inReadInterval` int(11) DEFAULT NULL,
  `outEmailAddress` varchar(75) DEFAULT NULL,
  `outCustom` tinyint(4) DEFAULT NULL,
  `outServerName` varchar(75) DEFAULT NULL,
  `outServerPort` int(11) DEFAULT NULL,
  `outUseSSL` tinyint(4) DEFAULT NULL,
  `outUserName` varchar(75) DEFAULT NULL,
  `outPassword` varchar(75) DEFAULT NULL,
  `allowAnonymous` tinyint(4) DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`mailingListId`),
  UNIQUE KEY `IX_76CE9CDD` (`groupId`,`categoryId`),
  UNIQUE KEY `IX_E858F170` (`uuid_`,`groupId`),
  KEY `IX_BFEB984F` (`active_`),
  KEY `IX_4115EC7A` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MBMailingList`
--

LOCK TABLES `MBMailingList` WRITE;
/*!40000 ALTER TABLE `MBMailingList` DISABLE KEYS */;
/*!40000 ALTER TABLE `MBMailingList` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MBMessage`
--

DROP TABLE IF EXISTS `MBMessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MBMessage` (
  `uuid_` varchar(75) DEFAULT NULL,
  `messageId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `categoryId` bigint(20) DEFAULT NULL,
  `threadId` bigint(20) DEFAULT NULL,
  `rootMessageId` bigint(20) DEFAULT NULL,
  `parentMessageId` bigint(20) DEFAULT NULL,
  `subject` varchar(75) DEFAULT NULL,
  `body` longtext,
  `format` varchar(75) DEFAULT NULL,
  `attachments` tinyint(4) DEFAULT NULL,
  `anonymous` tinyint(4) DEFAULT NULL,
  `priority` double DEFAULT NULL,
  `allowPingbacks` tinyint(4) DEFAULT NULL,
  `answer` tinyint(4) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`messageId`),
  UNIQUE KEY `IX_8D12316E` (`uuid_`,`groupId`),
  KEY `IX_51A8D44D` (`classNameId`,`classPK`),
  KEY `IX_F6687633` (`classNameId`,`classPK`,`status`),
  KEY `IX_B1432D30` (`companyId`),
  KEY `IX_1AD93C16` (`companyId`,`status`),
  KEY `IX_5B153FB2` (`groupId`),
  KEY `IX_1073AB9F` (`groupId`,`categoryId`),
  KEY `IX_4257DB85` (`groupId`,`categoryId`,`status`),
  KEY `IX_B674AB58` (`groupId`,`categoryId`,`threadId`),
  KEY `IX_CBFDBF0A` (`groupId`,`categoryId`,`threadId`,`answer`),
  KEY `IX_385E123E` (`groupId`,`categoryId`,`threadId`,`status`),
  KEY `IX_ED39AC98` (`groupId`,`status`),
  KEY `IX_8EB8C5EC` (`groupId`,`userId`),
  KEY `IX_377858D2` (`groupId`,`userId`,`status`),
  KEY `IX_75B95071` (`threadId`),
  KEY `IX_9D7C3B23` (`threadId`,`answer`),
  KEY `IX_A7038CD7` (`threadId`,`parentMessageId`),
  KEY `IX_9DC8E57` (`threadId`,`status`),
  KEY `IX_7A040C32` (`userId`),
  KEY `IX_59F9CE5C` (`userId`,`classNameId`),
  KEY `IX_ABEB6D07` (`userId`,`classNameId`,`classPK`),
  KEY `IX_4A4BB4ED` (`userId`,`classNameId`,`classPK`,`status`),
  KEY `IX_3321F142` (`userId`,`classNameId`,`status`),
  KEY `IX_C57B16BC` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MBMessage`
--

LOCK TABLES `MBMessage` WRITE;
/*!40000 ALTER TABLE `MBMessage` DISABLE KEYS */;
INSERT INTO `MBMessage` VALUES ('68fca0a5-5d42-41b5-9777-8e35ee73ec85',10176,10172,10154,10158,'','2013-07-10 10:34:14','2013-07-10 10:34:14',10002,10175,-1,10177,10176,0,'10175','10175','bbcode',0,1,0,0,0,0,10158,'','2013-07-10 10:34:14'),('a936e6b6-9df3-4447-aac4-f685b821ddeb',10184,10180,10154,10158,'','2013-07-10 10:34:14','2013-07-10 10:34:14',10002,10183,-1,10185,10184,0,'10183','10183','bbcode',0,1,0,0,0,0,10158,'','2013-07-10 10:34:14'),('613bfc55-feed-44d2-b818-a3ec9113d583',10314,10310,10154,10158,'','2013-07-10 10:34:19','2013-07-10 10:34:19',10002,10313,-1,10315,10314,0,'10313','10313','bbcode',0,1,0,0,0,0,10158,'','2013-07-10 10:34:19'),('c52d4d52-45f9-4361-a1da-3334fd879003',10324,10320,10154,10158,'','2013-07-10 10:34:20','2013-07-10 10:34:20',10002,10323,-1,10325,10324,0,'10323','10323','bbcode',0,1,0,0,0,0,10158,'','2013-07-10 10:34:20'),('c58a01fa-708e-4040-af5c-7e9e5f0d4eec',10333,10329,10154,10158,'','2013-07-10 10:34:20','2013-07-10 10:34:20',10002,10332,-1,10334,10333,0,'10332','10332','bbcode',0,1,0,0,0,0,10158,'','2013-07-10 10:34:20'),('44e0bd97-6c52-46bc-8554-38ee5a8e291f',10347,10338,10154,10158,'','2013-07-10 10:34:20','2013-07-10 10:34:20',10002,10346,-1,10348,10347,0,'10346','10346','bbcode',0,1,0,0,0,0,10158,'','2013-07-10 10:34:20'),('00fb6afd-dd5c-41b7-91cc-8e9a6b64004a',10353,10338,10154,10158,'','2013-07-10 10:34:21','2013-07-10 10:34:21',10002,10352,-1,10354,10353,0,'10352','10352','bbcode',0,1,0,0,0,0,10158,'','2013-07-10 10:34:21'),('bb803370-2bd8-403e-893c-22047426d5e5',10359,10338,10154,10158,'','2013-07-10 10:34:21','2013-07-10 10:34:21',10002,10358,-1,10360,10359,0,'10358','10358','bbcode',0,1,0,0,0,0,10158,'','2013-07-10 10:34:21'),('5bf1486c-2b5b-40d1-b480-d5a5211f23a7',10373,10364,10154,10158,'','2013-07-10 10:34:21','2013-07-10 10:34:21',10002,10372,-1,10374,10373,0,'10372','10372','bbcode',0,1,0,0,0,0,10158,'','2013-07-10 10:34:21'),('7aecf50c-1448-4edc-b267-4c2e8e7949b4',10381,10364,10154,10158,'','2013-07-10 10:34:21','2013-07-10 10:34:21',10002,10380,-1,10382,10381,0,'10380','10380','bbcode',0,1,0,0,0,0,10158,'','2013-07-10 10:34:21'),('198fb4a4-5b41-47dd-b481-cdb1a9273117',10387,10364,10154,10158,'','2013-07-10 10:34:21','2013-07-10 10:34:21',10002,10386,-1,10388,10387,0,'10386','10386','bbcode',0,1,0,0,0,0,10158,'','2013-07-10 10:34:21'),('51ef9cf7-6b8d-4c29-9533-68d6db5b9fd7',10393,10364,10154,10158,'','2013-07-10 10:34:21','2013-07-10 10:34:21',10002,10392,-1,10394,10393,0,'10392','10392','bbcode',0,1,0,0,0,0,10158,'','2013-07-10 10:34:21');
/*!40000 ALTER TABLE `MBMessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MBStatsUser`
--

DROP TABLE IF EXISTS `MBStatsUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MBStatsUser` (
  `statsUserId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `messageCount` int(11) DEFAULT NULL,
  `lastPostDate` datetime DEFAULT NULL,
  PRIMARY KEY (`statsUserId`),
  UNIQUE KEY `IX_9168E2C9` (`groupId`,`userId`),
  KEY `IX_A00A898F` (`groupId`),
  KEY `IX_D33A5445` (`groupId`,`userId`,`messageCount`),
  KEY `IX_847F92B5` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MBStatsUser`
--

LOCK TABLES `MBStatsUser` WRITE;
/*!40000 ALTER TABLE `MBStatsUser` DISABLE KEYS */;
/*!40000 ALTER TABLE `MBStatsUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MBThread`
--

DROP TABLE IF EXISTS `MBThread`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MBThread` (
  `threadId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `categoryId` bigint(20) DEFAULT NULL,
  `rootMessageId` bigint(20) DEFAULT NULL,
  `rootMessageUserId` bigint(20) DEFAULT NULL,
  `messageCount` int(11) DEFAULT NULL,
  `viewCount` int(11) DEFAULT NULL,
  `lastPostByUserId` bigint(20) DEFAULT NULL,
  `lastPostDate` datetime DEFAULT NULL,
  `priority` double DEFAULT NULL,
  `question` tinyint(4) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`threadId`),
  KEY `IX_41F6DC8A` (`categoryId`,`priority`),
  KEY `IX_95C0EA45` (`groupId`),
  KEY `IX_9A2D11B2` (`groupId`,`categoryId`),
  KEY `IX_50F1904A` (`groupId`,`categoryId`,`lastPostDate`),
  KEY `IX_485F7E98` (`groupId`,`categoryId`,`status`),
  KEY `IX_E1E7142B` (`groupId`,`status`),
  KEY `IX_AEDD9CB5` (`lastPostDate`,`priority`),
  KEY `IX_CC993ECB` (`rootMessageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MBThread`
--

LOCK TABLES `MBThread` WRITE;
/*!40000 ALTER TABLE `MBThread` DISABLE KEYS */;
INSERT INTO `MBThread` VALUES (10177,10172,10154,-1,10176,10158,1,0,0,'2013-07-10 10:34:14',0,0,0,10158,'','2013-07-10 10:34:14'),(10185,10180,10154,-1,10184,10158,1,0,0,'2013-07-10 10:34:14',0,0,0,10158,'','2013-07-10 10:34:14'),(10315,10310,10154,-1,10314,10158,1,0,0,'2013-07-10 10:34:19',0,0,0,10158,'','2013-07-10 10:34:19'),(10325,10320,10154,-1,10324,10158,1,0,0,'2013-07-10 10:34:20',0,0,0,10158,'','2013-07-10 10:34:20'),(10334,10329,10154,-1,10333,10158,1,0,0,'2013-07-10 10:34:20',0,0,0,10158,'','2013-07-10 10:34:20'),(10348,10338,10154,-1,10347,10158,1,0,0,'2013-07-10 10:34:20',0,0,0,10158,'','2013-07-10 10:34:20'),(10354,10338,10154,-1,10353,10158,1,0,0,'2013-07-10 10:34:21',0,0,0,10158,'','2013-07-10 10:34:21'),(10360,10338,10154,-1,10359,10158,1,0,0,'2013-07-10 10:34:21',0,0,0,10158,'','2013-07-10 10:34:21'),(10374,10364,10154,-1,10373,10158,1,0,0,'2013-07-10 10:34:21',0,0,0,10158,'','2013-07-10 10:34:21'),(10382,10364,10154,-1,10381,10158,1,0,0,'2013-07-10 10:34:21',0,0,0,10158,'','2013-07-10 10:34:21'),(10388,10364,10154,-1,10387,10158,1,0,0,'2013-07-10 10:34:21',0,0,0,10158,'','2013-07-10 10:34:21'),(10394,10364,10154,-1,10393,10158,1,0,0,'2013-07-10 10:34:21',0,0,0,10158,'','2013-07-10 10:34:21');
/*!40000 ALTER TABLE `MBThread` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MBThreadFlag`
--

DROP TABLE IF EXISTS `MBThreadFlag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MBThreadFlag` (
  `threadFlagId` bigint(20) NOT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `threadId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`threadFlagId`),
  KEY `IX_8CB0A24A` (`threadId`),
  KEY `IX_A28004B` (`userId`),
  KEY `IX_33781904` (`userId`,`threadId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MBThreadFlag`
--

LOCK TABLES `MBThreadFlag` WRITE;
/*!40000 ALTER TABLE `MBThreadFlag` DISABLE KEYS */;
/*!40000 ALTER TABLE `MBThreadFlag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MDRAction`
--

DROP TABLE IF EXISTS `MDRAction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MDRAction` (
  `uuid_` varchar(75) DEFAULT NULL,
  `actionId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `ruleGroupInstanceId` bigint(20) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `type_` varchar(255) DEFAULT NULL,
  `typeSettings` longtext,
  PRIMARY KEY (`actionId`),
  UNIQUE KEY `IX_75BE36AD` (`uuid_`,`groupId`),
  KEY `IX_FD90786C` (`ruleGroupInstanceId`),
  KEY `IX_77BB5E9D` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MDRAction`
--

LOCK TABLES `MDRAction` WRITE;
/*!40000 ALTER TABLE `MDRAction` DISABLE KEYS */;
/*!40000 ALTER TABLE `MDRAction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MDRRule`
--

DROP TABLE IF EXISTS `MDRRule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MDRRule` (
  `uuid_` varchar(75) DEFAULT NULL,
  `ruleId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `ruleGroupId` bigint(20) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `type_` varchar(255) DEFAULT NULL,
  `typeSettings` longtext,
  PRIMARY KEY (`ruleId`),
  UNIQUE KEY `IX_F3EFDCB3` (`uuid_`,`groupId`),
  KEY `IX_4F4293F1` (`ruleGroupId`),
  KEY `IX_EA63B9D7` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MDRRule`
--

LOCK TABLES `MDRRule` WRITE;
/*!40000 ALTER TABLE `MDRRule` DISABLE KEYS */;
/*!40000 ALTER TABLE `MDRRule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MDRRuleGroup`
--

DROP TABLE IF EXISTS `MDRRuleGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MDRRuleGroup` (
  `uuid_` varchar(75) DEFAULT NULL,
  `ruleGroupId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  PRIMARY KEY (`ruleGroupId`),
  UNIQUE KEY `IX_46665CC4` (`uuid_`,`groupId`),
  KEY `IX_5849891C` (`groupId`),
  KEY `IX_7F26B2A6` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MDRRuleGroup`
--

LOCK TABLES `MDRRuleGroup` WRITE;
/*!40000 ALTER TABLE `MDRRuleGroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `MDRRuleGroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MDRRuleGroupInstance`
--

DROP TABLE IF EXISTS `MDRRuleGroupInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MDRRuleGroupInstance` (
  `uuid_` varchar(75) DEFAULT NULL,
  `ruleGroupInstanceId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `ruleGroupId` bigint(20) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`ruleGroupInstanceId`),
  UNIQUE KEY `IX_808A0036` (`classNameId`,`classPK`,`ruleGroupId`),
  UNIQUE KEY `IX_9CBC6A39` (`uuid_`,`groupId`),
  KEY `IX_C95A08D8` (`classNameId`,`classPK`),
  KEY `IX_AFF28547` (`groupId`),
  KEY `IX_22DAB85C` (`groupId`,`classNameId`,`classPK`),
  KEY `IX_BF3E642B` (`ruleGroupId`),
  KEY `IX_B6A6BD91` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MDRRuleGroupInstance`
--

LOCK TABLES `MDRRuleGroupInstance` WRITE;
/*!40000 ALTER TABLE `MDRRuleGroupInstance` DISABLE KEYS */;
/*!40000 ALTER TABLE `MDRRuleGroupInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MembershipRequest`
--

DROP TABLE IF EXISTS `MembershipRequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MembershipRequest` (
  `membershipRequestId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `comments` longtext,
  `replyComments` longtext,
  `replyDate` datetime DEFAULT NULL,
  `replierUserId` bigint(20) DEFAULT NULL,
  `statusId` int(11) DEFAULT NULL,
  PRIMARY KEY (`membershipRequestId`),
  KEY `IX_8A1CC4B` (`groupId`),
  KEY `IX_C28C72EC` (`groupId`,`statusId`),
  KEY `IX_35AA8FA6` (`groupId`,`userId`,`statusId`),
  KEY `IX_66D70879` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MembershipRequest`
--

LOCK TABLES `MembershipRequest` WRITE;
/*!40000 ALTER TABLE `MembershipRequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `MembershipRequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrgGroupPermission`
--

DROP TABLE IF EXISTS `OrgGroupPermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrgGroupPermission` (
  `organizationId` bigint(20) NOT NULL,
  `groupId` bigint(20) NOT NULL,
  `permissionId` bigint(20) NOT NULL,
  PRIMARY KEY (`organizationId`,`groupId`,`permissionId`),
  KEY `IX_A425F71A` (`groupId`),
  KEY `IX_6C53DA4E` (`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrgGroupPermission`
--

LOCK TABLES `OrgGroupPermission` WRITE;
/*!40000 ALTER TABLE `OrgGroupPermission` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrgGroupPermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrgGroupRole`
--

DROP TABLE IF EXISTS `OrgGroupRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrgGroupRole` (
  `organizationId` bigint(20) NOT NULL,
  `groupId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`organizationId`,`groupId`,`roleId`),
  KEY `IX_4A527DD3` (`groupId`),
  KEY `IX_AB044D1C` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrgGroupRole`
--

LOCK TABLES `OrgGroupRole` WRITE;
/*!40000 ALTER TABLE `OrgGroupRole` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrgGroupRole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrgLabor`
--

DROP TABLE IF EXISTS `OrgLabor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrgLabor` (
  `orgLaborId` bigint(20) NOT NULL,
  `organizationId` bigint(20) DEFAULT NULL,
  `typeId` int(11) DEFAULT NULL,
  `sunOpen` int(11) DEFAULT NULL,
  `sunClose` int(11) DEFAULT NULL,
  `monOpen` int(11) DEFAULT NULL,
  `monClose` int(11) DEFAULT NULL,
  `tueOpen` int(11) DEFAULT NULL,
  `tueClose` int(11) DEFAULT NULL,
  `wedOpen` int(11) DEFAULT NULL,
  `wedClose` int(11) DEFAULT NULL,
  `thuOpen` int(11) DEFAULT NULL,
  `thuClose` int(11) DEFAULT NULL,
  `friOpen` int(11) DEFAULT NULL,
  `friClose` int(11) DEFAULT NULL,
  `satOpen` int(11) DEFAULT NULL,
  `satClose` int(11) DEFAULT NULL,
  PRIMARY KEY (`orgLaborId`),
  KEY `IX_6AF0D434` (`organizationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrgLabor`
--

LOCK TABLES `OrgLabor` WRITE;
/*!40000 ALTER TABLE `OrgLabor` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrgLabor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Organization_`
--

DROP TABLE IF EXISTS `Organization_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Organization_` (
  `organizationId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `parentOrganizationId` bigint(20) DEFAULT NULL,
  `treePath` longtext,
  `name` varchar(100) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `recursable` tinyint(4) DEFAULT NULL,
  `regionId` bigint(20) DEFAULT NULL,
  `countryId` bigint(20) DEFAULT NULL,
  `statusId` int(11) DEFAULT NULL,
  `comments` longtext,
  PRIMARY KEY (`organizationId`),
  UNIQUE KEY `IX_E301BDF5` (`companyId`,`name`),
  KEY `IX_834BCEB6` (`companyId`),
  KEY `IX_418E4522` (`companyId`,`parentOrganizationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Organization_`
--

LOCK TABLES `Organization_` WRITE;
/*!40000 ALTER TABLE `Organization_` DISABLE KEYS */;
/*!40000 ALTER TABLE `Organization_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PasswordPolicy`
--

DROP TABLE IF EXISTS `PasswordPolicy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PasswordPolicy` (
  `passwordPolicyId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `defaultPolicy` tinyint(4) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `changeable` tinyint(4) DEFAULT NULL,
  `changeRequired` tinyint(4) DEFAULT NULL,
  `minAge` bigint(20) DEFAULT NULL,
  `checkSyntax` tinyint(4) DEFAULT NULL,
  `allowDictionaryWords` tinyint(4) DEFAULT NULL,
  `minAlphanumeric` int(11) DEFAULT NULL,
  `minLength` int(11) DEFAULT NULL,
  `minLowerCase` int(11) DEFAULT NULL,
  `minNumbers` int(11) DEFAULT NULL,
  `minSymbols` int(11) DEFAULT NULL,
  `minUpperCase` int(11) DEFAULT NULL,
  `history` tinyint(4) DEFAULT NULL,
  `historyCount` int(11) DEFAULT NULL,
  `expireable` tinyint(4) DEFAULT NULL,
  `maxAge` bigint(20) DEFAULT NULL,
  `warningTime` bigint(20) DEFAULT NULL,
  `graceLimit` int(11) DEFAULT NULL,
  `lockout` tinyint(4) DEFAULT NULL,
  `maxFailure` int(11) DEFAULT NULL,
  `lockoutDuration` bigint(20) DEFAULT NULL,
  `requireUnlock` tinyint(4) DEFAULT NULL,
  `resetFailureCount` bigint(20) DEFAULT NULL,
  `resetTicketMaxAge` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`passwordPolicyId`),
  UNIQUE KEY `IX_3FBFA9F4` (`companyId`,`name`),
  KEY `IX_2C1142E` (`companyId`,`defaultPolicy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PasswordPolicy`
--

LOCK TABLES `PasswordPolicy` WRITE;
/*!40000 ALTER TABLE `PasswordPolicy` DISABLE KEYS */;
INSERT INTO `PasswordPolicy` VALUES (10195,10154,10158,'','2013-07-10 10:34:15','2013-07-10 10:34:15',1,'Default Password Policy','Default Password Policy',1,1,0,0,1,0,6,0,1,0,1,0,6,0,8640000,86400,0,0,3,0,1,600,86400);
/*!40000 ALTER TABLE `PasswordPolicy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PasswordPolicyRel`
--

DROP TABLE IF EXISTS `PasswordPolicyRel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PasswordPolicyRel` (
  `passwordPolicyRelId` bigint(20) NOT NULL,
  `passwordPolicyId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`passwordPolicyRelId`),
  KEY `IX_C3A17327` (`classNameId`,`classPK`),
  KEY `IX_CD25266E` (`passwordPolicyId`),
  KEY `IX_ED7CF243` (`passwordPolicyId`,`classNameId`,`classPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PasswordPolicyRel`
--

LOCK TABLES `PasswordPolicyRel` WRITE;
/*!40000 ALTER TABLE `PasswordPolicyRel` DISABLE KEYS */;
/*!40000 ALTER TABLE `PasswordPolicyRel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PasswordTracker`
--

DROP TABLE IF EXISTS `PasswordTracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PasswordTracker` (
  `passwordTrackerId` bigint(20) NOT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `password_` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`passwordTrackerId`),
  KEY `IX_326F75BD` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PasswordTracker`
--

LOCK TABLES `PasswordTracker` WRITE;
/*!40000 ALTER TABLE `PasswordTracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `PasswordTracker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Permission_`
--

DROP TABLE IF EXISTS `Permission_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Permission_` (
  `permissionId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `actionId` varchar(75) DEFAULT NULL,
  `resourceId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`permissionId`),
  UNIQUE KEY `IX_4D19C2B8` (`actionId`,`resourceId`),
  KEY `IX_F090C113` (`resourceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Permission_`
--

LOCK TABLES `Permission_` WRITE;
/*!40000 ALTER TABLE `Permission_` DISABLE KEYS */;
/*!40000 ALTER TABLE `Permission_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Phone`
--

DROP TABLE IF EXISTS `Phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Phone` (
  `phoneId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `number_` varchar(75) DEFAULT NULL,
  `extension` varchar(75) DEFAULT NULL,
  `typeId` int(11) DEFAULT NULL,
  `primary_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`phoneId`),
  KEY `IX_9F704A14` (`companyId`),
  KEY `IX_A2E4AFBA` (`companyId`,`classNameId`),
  KEY `IX_9A53569` (`companyId`,`classNameId`,`classPK`),
  KEY `IX_812CE07A` (`companyId`,`classNameId`,`classPK`,`primary_`),
  KEY `IX_F202B9CE` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Phone`
--

LOCK TABLES `Phone` WRITE;
/*!40000 ALTER TABLE `Phone` DISABLE KEYS */;
/*!40000 ALTER TABLE `Phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PluginSetting`
--

DROP TABLE IF EXISTS `PluginSetting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PluginSetting` (
  `pluginSettingId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `pluginId` varchar(75) DEFAULT NULL,
  `pluginType` varchar(75) DEFAULT NULL,
  `roles` longtext,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`pluginSettingId`),
  UNIQUE KEY `IX_7171B2E8` (`companyId`,`pluginId`,`pluginType`),
  KEY `IX_B9746445` (`companyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PluginSetting`
--

LOCK TABLES `PluginSetting` WRITE;
/*!40000 ALTER TABLE `PluginSetting` DISABLE KEYS */;
/*!40000 ALTER TABLE `PluginSetting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PollsChoice`
--

DROP TABLE IF EXISTS `PollsChoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PollsChoice` (
  `uuid_` varchar(75) DEFAULT NULL,
  `choiceId` bigint(20) NOT NULL,
  `questionId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`choiceId`),
  UNIQUE KEY `IX_D76DD2CF` (`questionId`,`name`),
  KEY `IX_EC370F10` (`questionId`),
  KEY `IX_6660B399` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PollsChoice`
--

LOCK TABLES `PollsChoice` WRITE;
/*!40000 ALTER TABLE `PollsChoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `PollsChoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PollsQuestion`
--

DROP TABLE IF EXISTS `PollsQuestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PollsQuestion` (
  `uuid_` varchar(75) DEFAULT NULL,
  `questionId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `title` longtext,
  `description` longtext,
  `expirationDate` datetime DEFAULT NULL,
  `lastVoteDate` datetime DEFAULT NULL,
  PRIMARY KEY (`questionId`),
  UNIQUE KEY `IX_F3C9F36` (`uuid_`,`groupId`),
  KEY `IX_9FF342EA` (`groupId`),
  KEY `IX_51F087F4` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PollsQuestion`
--

LOCK TABLES `PollsQuestion` WRITE;
/*!40000 ALTER TABLE `PollsQuestion` DISABLE KEYS */;
/*!40000 ALTER TABLE `PollsQuestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PollsVote`
--

DROP TABLE IF EXISTS `PollsVote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PollsVote` (
  `voteId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `questionId` bigint(20) DEFAULT NULL,
  `choiceId` bigint(20) DEFAULT NULL,
  `voteDate` datetime DEFAULT NULL,
  PRIMARY KEY (`voteId`),
  UNIQUE KEY `IX_1BBFD4D3` (`questionId`,`userId`),
  KEY `IX_D5DF7B54` (`choiceId`),
  KEY `IX_12112599` (`questionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PollsVote`
--

LOCK TABLES `PollsVote` WRITE;
/*!40000 ALTER TABLE `PollsVote` DISABLE KEYS */;
/*!40000 ALTER TABLE `PollsVote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PortalPreferences`
--

DROP TABLE IF EXISTS `PortalPreferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PortalPreferences` (
  `portalPreferencesId` bigint(20) NOT NULL,
  `ownerId` bigint(20) DEFAULT NULL,
  `ownerType` int(11) DEFAULT NULL,
  `preferences` longtext,
  PRIMARY KEY (`portalPreferencesId`),
  KEY `IX_D1F795F1` (`ownerId`,`ownerType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PortalPreferences`
--

LOCK TABLES `PortalPreferences` WRITE;
/*!40000 ALTER TABLE `PortalPreferences` DISABLE KEYS */;
INSERT INTO `PortalPreferences` VALUES (10153,0,1,'<portlet-preferences />'),(10160,10154,1,'<portlet-preferences />'),(10405,10158,4,'<portlet-preferences />');
/*!40000 ALTER TABLE `PortalPreferences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Portlet`
--

DROP TABLE IF EXISTS `Portlet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Portlet` (
  `id_` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `portletId` varchar(200) DEFAULT NULL,
  `roles` longtext,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `IX_12B5E51D` (`companyId`,`portletId`),
  KEY `IX_80CC9508` (`companyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Portlet`
--

LOCK TABLES `Portlet` WRITE;
/*!40000 ALTER TABLE `Portlet` DISABLE KEYS */;
INSERT INTO `Portlet` VALUES (10202,10154,'98','',1),(10203,10154,'66','',1),(10204,10154,'180','',1),(10205,10154,'27','',1),(10206,10154,'152','',1),(10207,10154,'134','',1),(10208,10154,'130','',1),(10209,10154,'122','',1),(10210,10154,'36','',1),(10211,10154,'26','',1),(10212,10154,'104','',1),(10213,10154,'175','',1),(10214,10154,'153','',1),(10215,10154,'64','',1),(10216,10154,'129','',1),(10217,10154,'179','',1),(10218,10154,'173','',1),(10219,10154,'100','',1),(10220,10154,'19','',1),(10221,10154,'157','',1),(10222,10154,'128','',1),(10223,10154,'181','',1),(10224,10154,'154','',1),(10225,10154,'148','',1),(10226,10154,'11','',1),(10227,10154,'29','',1),(10228,10154,'158','',1),(10229,10154,'178','',1),(10230,10154,'8','',1),(10231,10154,'58','',1),(10232,10154,'71','',1),(10233,10154,'97','',1),(10234,10154,'39','',1),(10235,10154,'177','',1),(10236,10154,'85','',1),(10237,10154,'118','',1),(10238,10154,'107','',1),(10239,10154,'30','',1),(10240,10154,'147','',1),(10241,10154,'48','',1),(10242,10154,'125','',1),(10243,10154,'161','',1),(10244,10154,'146','',1),(10245,10154,'62','',1),(10246,10154,'162','',1),(10247,10154,'176','',1),(10248,10154,'108','',1),(10249,10154,'84','',1),(10250,10154,'101','',1),(10251,10154,'121','',1),(10252,10154,'143','',1),(10253,10154,'77','',1),(10254,10154,'167','',1),(10255,10154,'115','',1),(10256,10154,'56','',1),(10257,10154,'16','',1),(10258,10154,'111','',1),(10259,10154,'3','',1),(10260,10154,'23','',1),(10261,10154,'20','',1),(10262,10154,'83','',1),(10263,10154,'164','',1),(10264,10154,'99','',1),(10265,10154,'70','',1),(10266,10154,'141','',1),(10267,10154,'9','',1),(10268,10154,'28','',1),(10269,10154,'137','',1),(10270,10154,'47','',1),(10271,10154,'15','',1),(10272,10154,'116','',1),(10273,10154,'82','',1),(10274,10154,'151','',1),(10275,10154,'54','',1),(10276,10154,'34','',1),(10277,10154,'169','',1),(10278,10154,'132','',1),(10279,10154,'61','',1),(10280,10154,'73','',1),(10281,10154,'31','',1),(10282,10154,'136','',1),(10283,10154,'50','',1),(10284,10154,'127','',1),(10285,10154,'25','',1),(10286,10154,'166','',1),(10287,10154,'33','',1),(10288,10154,'150','',1),(10289,10154,'114','',1),(10290,10154,'149','',1),(10291,10154,'67','',1),(10292,10154,'110','',1),(10293,10154,'59','',1),(10294,10154,'135','',1),(10295,10154,'131','',1),(10296,10154,'102','',1);
/*!40000 ALTER TABLE `Portlet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PortletItem`
--

DROP TABLE IF EXISTS `PortletItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PortletItem` (
  `portletItemId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `portletId` varchar(75) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`portletItemId`),
  KEY `IX_96BDD537` (`groupId`,`classNameId`),
  KEY `IX_D699243F` (`groupId`,`name`,`portletId`,`classNameId`),
  KEY `IX_2C61314E` (`groupId`,`portletId`),
  KEY `IX_E922D6C0` (`groupId`,`portletId`,`classNameId`),
  KEY `IX_8E71167F` (`groupId`,`portletId`,`classNameId`,`name`),
  KEY `IX_33B8CE8D` (`groupId`,`portletId`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PortletItem`
--

LOCK TABLES `PortletItem` WRITE;
/*!40000 ALTER TABLE `PortletItem` DISABLE KEYS */;
/*!40000 ALTER TABLE `PortletItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PortletPreferences`
--

DROP TABLE IF EXISTS `PortletPreferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PortletPreferences` (
  `portletPreferencesId` bigint(20) NOT NULL,
  `ownerId` bigint(20) DEFAULT NULL,
  `ownerType` int(11) DEFAULT NULL,
  `plid` bigint(20) DEFAULT NULL,
  `portletId` varchar(200) DEFAULT NULL,
  `preferences` longtext,
  PRIMARY KEY (`portletPreferencesId`),
  UNIQUE KEY `IX_C7057FF7` (`ownerId`,`ownerType`,`plid`,`portletId`),
  KEY `IX_E4F13E6E` (`ownerId`,`ownerType`,`plid`),
  KEY `IX_D5EDA3A1` (`ownerType`,`plid`,`portletId`),
  KEY `IX_F15C1C4F` (`plid`),
  KEY `IX_D340DB76` (`plid`,`portletId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PortletPreferences`
--

LOCK TABLES `PortletPreferences` WRITE;
/*!40000 ALTER TABLE `PortletPreferences` DISABLE KEYS */;
INSERT INTO `PortletPreferences` VALUES (10351,0,3,10346,'3','<portlet-preferences><preference><name>portletSetupShowBorders</name><value>false</value></preference></portlet-preferences>'),(10357,0,3,10352,'101_INSTANCE_NACG9Qd7cKf1','<portlet-preferences><preference><name>portletSetupUseCustomTitle</name><value>true</value></preference><preference><name>anyAssetType</name><value>false</value></preference><preference><name>classNameIds</name><value>10009</value></preference><preference><name>portletSetupTitle_en_US</name><value>Upcoming Events</value></preference></portlet-preferences>'),(10377,0,3,10372,'3','<portlet-preferences><preference><name>portletSetupShowBorders</name><value>false</value></preference></portlet-preferences>'),(10378,0,3,10372,'82','<portlet-preferences><preference><name>displayStyle</name><value>3</value></preference></portlet-preferences>'),(10379,0,3,10372,'101_INSTANCE_Svf0QgfbEiVg','<portlet-preferences><preference><name>portletSetupUseCustomTitle</name><value>true</value></preference><preference><name>portletSetupTitle_en_US</name><value>Recent Content</value></preference></portlet-preferences>'),(10385,0,3,10380,'20','<portlet-preferences><preference><name>portletSetupShowBorders</name><value>false</value></preference></portlet-preferences>'),(10391,0,3,10386,'101_INSTANCE_yA8x8bByfliv','<portlet-preferences><preference><name>portletSetupUseCustomTitle</name><value>true</value></preference><preference><name>anyAssetType</name><value>false</value></preference><preference><name>classNameIds</name><value>10009</value></preference><preference><name>portletSetupTitle_en_US</name><value>Upcoming Events</value></preference></portlet-preferences>'),(10397,0,3,10392,'39_INSTANCE_BLhgNLhU4elv','<portlet-preferences><preference><name>portletSetupUseCustomTitle</name><value>true</value></preference><preference><name>expandedEntriesPerFeed</name><value>3</value></preference><preference><name>urls</name><value>http://partners.userland.com/nytRss/technology.xml</value></preference><preference><name>items-per-channel</name><value>2</value></preference><preference><name>portletSetupTitle_en_US</name><value>Technology news</value></preference></portlet-preferences>'),(10398,0,3,10392,'39_INSTANCE_7KqnBsHGzom2','<portlet-preferences><preference><name>portletSetupUseCustomTitle</name><value>true</value></preference><preference><name>expandedEntriesPerFeed</name><value>0</value></preference><preference><name>urls</name><value>http://www.liferay.com/en/about-us/news/-/blogs/rss</value></preference><preference><name>titles</name><value>Liferay Press Releases</value></preference><preference><name>items-per-channel</name><value>2</value></preference><preference><name>portletSetupTitle_en_US</name><value>Liferay news</value></preference></portlet-preferences>');
/*!40000 ALTER TABLE `PortletPreferences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUARTZ_BLOB_TRIGGERS`
--

DROP TABLE IF EXISTS `QUARTZ_BLOB_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUARTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` longblob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUARTZ_BLOB_TRIGGERS`
--

LOCK TABLES `QUARTZ_BLOB_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QUARTZ_BLOB_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUARTZ_BLOB_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUARTZ_CALENDARS`
--

DROP TABLE IF EXISTS `QUARTZ_CALENDARS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUARTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` longblob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUARTZ_CALENDARS`
--

LOCK TABLES `QUARTZ_CALENDARS` WRITE;
/*!40000 ALTER TABLE `QUARTZ_CALENDARS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUARTZ_CALENDARS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUARTZ_CRON_TRIGGERS`
--

DROP TABLE IF EXISTS `QUARTZ_CRON_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUARTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUARTZ_CRON_TRIGGERS`
--

LOCK TABLES `QUARTZ_CRON_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QUARTZ_CRON_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUARTZ_CRON_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUARTZ_FIRED_TRIGGERS`
--

DROP TABLE IF EXISTS `QUARTZ_FIRED_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUARTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(20) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` tinyint(4) DEFAULT NULL,
  `REQUESTS_RECOVERY` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IX_BE3835E5` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IX_4BD722BM` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IX_204D31E8` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IX_339E078M` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IX_5005E3AF` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IX_BC2F03B0` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUARTZ_FIRED_TRIGGERS`
--

LOCK TABLES `QUARTZ_FIRED_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QUARTZ_FIRED_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUARTZ_FIRED_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUARTZ_JOB_DETAILS`
--

DROP TABLE IF EXISTS `QUARTZ_JOB_DETAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUARTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` tinyint(4) NOT NULL,
  `IS_NONCONCURRENT` tinyint(4) NOT NULL,
  `IS_UPDATE_DATA` tinyint(4) NOT NULL,
  `REQUESTS_RECOVERY` tinyint(4) NOT NULL,
  `JOB_DATA` longblob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IX_88328984` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IX_779BCA37` (`SCHED_NAME`,`REQUESTS_RECOVERY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUARTZ_JOB_DETAILS`
--

LOCK TABLES `QUARTZ_JOB_DETAILS` WRITE;
/*!40000 ALTER TABLE `QUARTZ_JOB_DETAILS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUARTZ_JOB_DETAILS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUARTZ_LOCKS`
--

DROP TABLE IF EXISTS `QUARTZ_LOCKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUARTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUARTZ_LOCKS`
--

LOCK TABLES `QUARTZ_LOCKS` WRITE;
/*!40000 ALTER TABLE `QUARTZ_LOCKS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUARTZ_LOCKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUARTZ_PAUSED_TRIGGER_GRPS`
--

DROP TABLE IF EXISTS `QUARTZ_PAUSED_TRIGGER_GRPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUARTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUARTZ_PAUSED_TRIGGER_GRPS`
--

LOCK TABLES `QUARTZ_PAUSED_TRIGGER_GRPS` WRITE;
/*!40000 ALTER TABLE `QUARTZ_PAUSED_TRIGGER_GRPS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUARTZ_PAUSED_TRIGGER_GRPS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUARTZ_SCHEDULER_STATE`
--

DROP TABLE IF EXISTS `QUARTZ_SCHEDULER_STATE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUARTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(20) NOT NULL,
  `CHECKIN_INTERVAL` bigint(20) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUARTZ_SCHEDULER_STATE`
--

LOCK TABLES `QUARTZ_SCHEDULER_STATE` WRITE;
/*!40000 ALTER TABLE `QUARTZ_SCHEDULER_STATE` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUARTZ_SCHEDULER_STATE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUARTZ_SIMPLE_TRIGGERS`
--

DROP TABLE IF EXISTS `QUARTZ_SIMPLE_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUARTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(20) NOT NULL,
  `REPEAT_INTERVAL` bigint(20) NOT NULL,
  `TIMES_TRIGGERED` bigint(20) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUARTZ_SIMPLE_TRIGGERS`
--

LOCK TABLES `QUARTZ_SIMPLE_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QUARTZ_SIMPLE_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUARTZ_SIMPLE_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUARTZ_SIMPROP_TRIGGERS`
--

DROP TABLE IF EXISTS `QUARTZ_SIMPROP_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUARTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` tinyint(4) DEFAULT NULL,
  `BOOL_PROP_2` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUARTZ_SIMPROP_TRIGGERS`
--

LOCK TABLES `QUARTZ_SIMPROP_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QUARTZ_SIMPROP_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUARTZ_SIMPROP_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUARTZ_TRIGGERS`
--

DROP TABLE IF EXISTS `QUARTZ_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUARTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(20) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(20) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(20) NOT NULL,
  `END_TIME` bigint(20) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` int(11) DEFAULT NULL,
  `JOB_DATA` longblob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IX_186442A4` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IX_1BA1F9DC` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IX_91CA7CCE` (`SCHED_NAME`,`TRIGGER_GROUP`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`,`MISFIRE_INSTR`),
  KEY `IX_D219AFDE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IX_A85822A0` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IX_8AA50BE1` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IX_EEFE382A` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IX_F026CF4C` (`SCHED_NAME`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IX_F2DD7C7E` (`SCHED_NAME`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`,`MISFIRE_INSTR`),
  KEY `IX_1F92813C` (`SCHED_NAME`,`NEXT_FIRE_TIME`,`MISFIRE_INSTR`),
  KEY `IX_99108B6E` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IX_CD7132D0` (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUARTZ_TRIGGERS`
--

LOCK TABLES `QUARTZ_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QUARTZ_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUARTZ_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RatingsEntry`
--

DROP TABLE IF EXISTS `RatingsEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RatingsEntry` (
  `entryId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `score` double DEFAULT NULL,
  PRIMARY KEY (`entryId`),
  UNIQUE KEY `IX_B47E3C11` (`userId`,`classNameId`,`classPK`),
  KEY `IX_16184D57` (`classNameId`,`classPK`),
  KEY `IX_A1A8CB8B` (`classNameId`,`classPK`,`score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RatingsEntry`
--

LOCK TABLES `RatingsEntry` WRITE;
/*!40000 ALTER TABLE `RatingsEntry` DISABLE KEYS */;
/*!40000 ALTER TABLE `RatingsEntry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RatingsStats`
--

DROP TABLE IF EXISTS `RatingsStats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RatingsStats` (
  `statsId` bigint(20) NOT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `totalEntries` int(11) DEFAULT NULL,
  `totalScore` double DEFAULT NULL,
  `averageScore` double DEFAULT NULL,
  PRIMARY KEY (`statsId`),
  UNIQUE KEY `IX_A6E99284` (`classNameId`,`classPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RatingsStats`
--

LOCK TABLES `RatingsStats` WRITE;
/*!40000 ALTER TABLE `RatingsStats` DISABLE KEYS */;
/*!40000 ALTER TABLE `RatingsStats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Region`
--

DROP TABLE IF EXISTS `Region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Region` (
  `regionId` bigint(20) NOT NULL,
  `countryId` bigint(20) DEFAULT NULL,
  `regionCode` varchar(75) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`regionId`),
  UNIQUE KEY `IX_A2635F5C` (`countryId`,`regionCode`),
  KEY `IX_2D9A426F` (`active_`),
  KEY `IX_16D87CA7` (`countryId`),
  KEY `IX_11FB3E42` (`countryId`,`active_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Region`
--

LOCK TABLES `Region` WRITE;
/*!40000 ALTER TABLE `Region` DISABLE KEYS */;
INSERT INTO `Region` VALUES (1001,1,'AB','Alberta',1),(1002,1,'BC','British Columbia',1),(1003,1,'MB','Manitoba',1),(1004,1,'NB','New Brunswick',1),(1005,1,'NL','Newfoundland and Labrador',1),(1006,1,'NT','Northwest Territories',1),(1007,1,'NS','Nova Scotia',1),(1008,1,'NU','Nunavut',1),(1009,1,'ON','Ontario',1),(1010,1,'PE','Prince Edward Island',1),(1011,1,'QC','Quebec',1),(1012,1,'SK','Saskatchewan',1),(1013,1,'YT','Yukon',1),(2001,2,'CN-34','Anhui',1),(2002,2,'CN-92','Aomen',1),(2003,2,'CN-11','Beijing',1),(2004,2,'CN-50','Chongqing',1),(2005,2,'CN-35','Fujian',1),(2006,2,'CN-62','Gansu',1),(2007,2,'CN-44','Guangdong',1),(2008,2,'CN-45','Guangxi',1),(2009,2,'CN-52','Guizhou',1),(2010,2,'CN-46','Hainan',1),(2011,2,'CN-13','Hebei',1),(2012,2,'CN-23','Heilongjiang',1),(2013,2,'CN-41','Henan',1),(2014,2,'CN-42','Hubei',1),(2015,2,'CN-43','Hunan',1),(2016,2,'CN-32','Jiangsu',1),(2017,2,'CN-36','Jiangxi',1),(2018,2,'CN-22','Jilin',1),(2019,2,'CN-21','Liaoning',1),(2020,2,'CN-15','Nei Mongol',1),(2021,2,'CN-64','Ningxia',1),(2022,2,'CN-63','Qinghai',1),(2023,2,'CN-61','Shaanxi',1),(2024,2,'CN-37','Shandong',1),(2025,2,'CN-31','Shanghai',1),(2026,2,'CN-14','Shanxi',1),(2027,2,'CN-51','Sichuan',1),(2028,2,'CN-71','Taiwan',1),(2029,2,'CN-12','Tianjin',1),(2030,2,'CN-91','Xianggang',1),(2031,2,'CN-65','Xinjiang',1),(2032,2,'CN-54','Xizang',1),(2033,2,'CN-53','Yunnan',1),(2034,2,'CN-33','Zhejiang',1),(3001,3,'A','Alsace',1),(3002,3,'B','Aquitaine',1),(3003,3,'C','Auvergne',1),(3004,3,'P','Basse-Normandie',1),(3005,3,'D','Bourgogne',1),(3006,3,'E','Bretagne',1),(3007,3,'F','Centre',1),(3008,3,'G','Champagne-Ardenne',1),(3009,3,'H','Corse',1),(3010,3,'GF','Guyane',1),(3011,3,'I','Franche Comté',1),(3012,3,'GP','Guadeloupe',1),(3013,3,'Q','Haute-Normandie',1),(3014,3,'J','Île-de-France',1),(3015,3,'K','Languedoc-Roussillon',1),(3016,3,'L','Limousin',1),(3017,3,'M','Lorraine',1),(3018,3,'MQ','Martinique',1),(3019,3,'N','Midi-Pyrénées',1),(3020,3,'O','Nord Pas de Calais',1),(3021,3,'R','Pays de la Loire',1),(3022,3,'S','Picardie',1),(3023,3,'T','Poitou-Charentes',1),(3024,3,'U','Provence-Alpes-Côte-d\'Azur',1),(3025,3,'RE','Réunion',1),(3026,3,'V','Rhône-Alpes',1),(4001,4,'BW','Baden-Württemberg',1),(4002,4,'BY','Bayern',1),(4003,4,'BE','Berlin',1),(4004,4,'BR','Brandenburg',1),(4005,4,'HB','Bremen',1),(4006,4,'HH','Hamburg',1),(4007,4,'HE','Hessen',1),(4008,4,'MV','Mecklenburg-Vorpommern',1),(4009,4,'NI','Niedersachsen',1),(4010,4,'NW','Nordrhein-Westfalen',1),(4011,4,'RP','Rheinland-Pfalz',1),(4012,4,'SL','Saarland',1),(4013,4,'SN','Sachsen',1),(4014,4,'ST','Sachsen-Anhalt',1),(4015,4,'SH','Schleswig-Holstein',1),(4016,4,'TH','Thüringen',1),(8001,8,'AG','Agrigento',1),(8002,8,'AL','Alessandria',1),(8003,8,'AN','Ancona',1),(8004,8,'AO','Aosta',1),(8005,8,'AR','Arezzo',1),(8006,8,'AP','Ascoli Piceno',1),(8007,8,'AT','Asti',1),(8008,8,'AV','Avellino',1),(8009,8,'BA','Bari',1),(8010,8,'BT','Barletta-Andria-Trani',1),(8011,8,'BL','Belluno',1),(8012,8,'BN','Benevento',1),(8013,8,'BG','Bergamo',1),(8014,8,'BI','Biella',1),(8015,8,'BO','Bologna',1),(8016,8,'BZ','Bolzano',1),(8017,8,'BS','Brescia',1),(8018,8,'BR','Brindisi',1),(8019,8,'CA','Cagliari',1),(8020,8,'CL','Caltanissetta',1),(8021,8,'CB','Campobasso',1),(8022,8,'CI','Carbonia-Iglesias',1),(8023,8,'CE','Caserta',1),(8024,8,'CT','Catania',1),(8025,8,'CZ','Catanzaro',1),(8026,8,'CH','Chieti',1),(8027,8,'CO','Como',1),(8028,8,'CS','Cosenza',1),(8029,8,'CR','Cremona',1),(8030,8,'KR','Crotone',1),(8031,8,'CN','Cuneo',1),(8032,8,'EN','Enna',1),(8033,8,'FM','Fermo',1),(8034,8,'FE','Ferrara',1),(8035,8,'FI','Firenze',1),(8036,8,'FG','Foggia',1),(8037,8,'FC','Forli-Cesena',1),(8038,8,'FR','Frosinone',1),(8039,8,'GE','Genova',1),(8040,8,'GO','Gorizia',1),(8041,8,'GR','Grosseto',1),(8042,8,'IM','Imperia',1),(8043,8,'IS','Isernia',1),(8044,8,'AQ','L\'Aquila',1),(8045,8,'SP','La Spezia',1),(8046,8,'LT','Latina',1),(8047,8,'LE','Lecce',1),(8048,8,'LC','Lecco',1),(8049,8,'LI','Livorno',1),(8050,8,'LO','Lodi',1),(8051,8,'LU','Lucca',1),(8052,8,'MC','Macerata',1),(8053,8,'MN','Mantova',1),(8054,8,'MS','Massa-Carrara',1),(8055,8,'MT','Matera',1),(8056,8,'MA','Medio Campidano',1),(8057,8,'ME','Messina',1),(8058,8,'MI','Milano',1),(8059,8,'MO','Modena',1),(8060,8,'MZ','Monza',1),(8061,8,'NA','Napoli',1),(8062,8,'NO','Novara',1),(8063,8,'NU','Nuoro',1),(8064,8,'OG','Ogliastra',1),(8065,8,'OT','Olbia-Tempio',1),(8066,8,'OR','Oristano',1),(8067,8,'PD','Padova',1),(8068,8,'PA','Palermo',1),(8069,8,'PR','Parma',1),(8070,8,'PV','Pavia',1),(8071,8,'PG','Perugia',1),(8072,8,'PU','Pesaro e Urbino',1),(8073,8,'PE','Pescara',1),(8074,8,'PC','Piacenza',1),(8075,8,'PI','Pisa',1),(8076,8,'PT','Pistoia',1),(8077,8,'PN','Pordenone',1),(8078,8,'PZ','Potenza',1),(8079,8,'PO','Prato',1),(8080,8,'RG','Ragusa',1),(8081,8,'RA','Ravenna',1),(8082,8,'RC','Reggio Calabria',1),(8083,8,'RE','Reggio Emilia',1),(8084,8,'RI','Rieti',1),(8085,8,'RN','Rimini',1),(8086,8,'RM','Roma',1),(8087,8,'RO','Rovigo',1),(8088,8,'SA','Salerno',1),(8089,8,'SS','Sassari',1),(8090,8,'SV','Savona',1),(8091,8,'SI','Siena',1),(8092,8,'SR','Siracusa',1),(8093,8,'SO','Sondrio',1),(8094,8,'TA','Taranto',1),(8095,8,'TE','Teramo',1),(8096,8,'TR','Terni',1),(8097,8,'TO','Torino',1),(8098,8,'TP','Trapani',1),(8099,8,'TN','Trento',1),(8100,8,'TV','Treviso',1),(8101,8,'TS','Trieste',1),(8102,8,'UD','Udine',1),(8103,8,'VA','Varese',1),(8104,8,'VE','Venezia',1),(8105,8,'VB','Verbano-Cusio-Ossola',1),(8106,8,'VC','Vercelli',1),(8107,8,'VR','Verona',1),(8108,8,'VV','Vibo Valentia',1),(8109,8,'VI','Vicenza',1),(8110,8,'VT','Viterbo',1),(11001,11,'DR','Drenthe',1),(11002,11,'FL','Flevoland',1),(11003,11,'FR','Friesland',1),(11004,11,'GE','Gelderland',1),(11005,11,'GR','Groningen',1),(11006,11,'LI','Limburg',1),(11007,11,'NB','Noord-Brabant',1),(11008,11,'NH','Noord-Holland',1),(11009,11,'OV','Overijssel',1),(11010,11,'UT','Utrecht',1),(11011,11,'ZE','Zeeland',1),(11012,11,'ZH','Zuid-Holland',1),(15001,15,'AN','Andalusia',1),(15002,15,'AR','Aragon',1),(15003,15,'AS','Asturias',1),(15004,15,'IB','Balearic Islands',1),(15005,15,'PV','Basque Country',1),(15006,15,'CN','Canary Islands',1),(15007,15,'CB','Cantabria',1),(15008,15,'CL','Castile and Leon',1),(15009,15,'CM','Castile-La Mancha',1),(15010,15,'CT','Catalonia',1),(15011,15,'CE','Ceuta',1),(15012,15,'EX','Extremadura',1),(15013,15,'GA','Galicia',1),(15014,15,'LO','La Rioja',1),(15015,15,'M','Madrid',1),(15016,15,'ML','Melilla',1),(15017,15,'MU','Murcia',1),(15018,15,'NA','Navarra',1),(15019,15,'VC','Valencia',1),(19001,19,'AL','Alabama',1),(19002,19,'AK','Alaska',1),(19003,19,'AZ','Arizona',1),(19004,19,'AR','Arkansas',1),(19005,19,'CA','California',1),(19006,19,'CO','Colorado',1),(19007,19,'CT','Connecticut',1),(19008,19,'DC','District of Columbia',1),(19009,19,'DE','Delaware',1),(19010,19,'FL','Florida',1),(19011,19,'GA','Georgia',1),(19012,19,'HI','Hawaii',1),(19013,19,'ID','Idaho',1),(19014,19,'IL','Illinois',1),(19015,19,'IN','Indiana',1),(19016,19,'IA','Iowa',1),(19017,19,'KS','Kansas',1),(19018,19,'KY','Kentucky ',1),(19019,19,'LA','Louisiana ',1),(19020,19,'ME','Maine',1),(19021,19,'MD','Maryland',1),(19022,19,'MA','Massachusetts',1),(19023,19,'MI','Michigan',1),(19024,19,'MN','Minnesota',1),(19025,19,'MS','Mississippi',1),(19026,19,'MO','Missouri',1),(19027,19,'MT','Montana',1),(19028,19,'NE','Nebraska',1),(19029,19,'NV','Nevada',1),(19030,19,'NH','New Hampshire',1),(19031,19,'NJ','New Jersey',1),(19032,19,'NM','New Mexico',1),(19033,19,'NY','New York',1),(19034,19,'NC','North Carolina',1),(19035,19,'ND','North Dakota',1),(19036,19,'OH','Ohio',1),(19037,19,'OK','Oklahoma ',1),(19038,19,'OR','Oregon',1),(19039,19,'PA','Pennsylvania',1),(19040,19,'PR','Puerto Rico',1),(19041,19,'RI','Rhode Island',1),(19042,19,'SC','South Carolina',1),(19043,19,'SD','South Dakota',1),(19044,19,'TN','Tennessee',1),(19045,19,'TX','Texas',1),(19046,19,'UT','Utah',1),(19047,19,'VT','Vermont',1),(19048,19,'VA','Virginia',1),(19049,19,'WA','Washington',1),(19050,19,'WV','West Virginia',1),(19051,19,'WI','Wisconsin',1),(19052,19,'WY','Wyoming',1),(32001,32,'ACT','Australian Capital Territory',1),(32002,32,'NSW','New South Wales',1),(32003,32,'NT','Northern Territory',1),(32004,32,'QLD','Queensland',1),(32005,32,'SA','South Australia',1),(32006,32,'TAS','Tasmania',1),(32007,32,'VIC','Victoria',1),(32008,32,'WA','Western Australia',1),(144001,144,'MX-AGS','Aguascalientes',1),(144002,144,'MX-BCN','Baja California',1),(144003,144,'MX-BCS','Baja California Sur',1),(144004,144,'MX-CAM','Campeche',1),(144005,144,'MX-CHP','Chiapas',1),(144006,144,'MX-CHI','Chihuahua',1),(144007,144,'MX-COA','Coahuila',1),(144008,144,'MX-COL','Colima',1),(144009,144,'MX-DUR','Durango',1),(144010,144,'MX-GTO','Guanajuato',1),(144011,144,'MX-GRO','Guerrero',1),(144012,144,'MX-HGO','Hidalgo',1),(144013,144,'MX-JAL','Jalisco',1),(144014,144,'MX-MEX','Mexico',1),(144015,144,'MX-MIC','Michoacan',1),(144016,144,'MX-MOR','Morelos',1),(144017,144,'MX-NAY','Nayarit',1),(144018,144,'MX-NLE','Nuevo Leon',1),(144019,144,'MX-OAX','Oaxaca',1),(144020,144,'MX-PUE','Puebla',1),(144021,144,'MX-QRO','Queretaro',1),(144023,144,'MX-ROO','Quintana Roo',1),(144024,144,'MX-SLP','San Luis Potosí',1),(144025,144,'MX-SIN','Sinaloa',1),(144026,144,'MX-SON','Sonora',1),(144027,144,'MX-TAB','Tabasco',1),(144028,144,'MX-TAM','Tamaulipas',1),(144029,144,'MX-TLX','Tlaxcala',1),(144030,144,'MX-VER','Veracruz',1),(144031,144,'MX-YUC','Yucatan',1),(144032,144,'MX-ZAC','Zacatecas',1),(164001,164,'01','Østfold',1),(164002,164,'02','Akershus',1),(164003,164,'03','Oslo',1),(164004,164,'04','Hedmark',1),(164005,164,'05','Oppland',1),(164006,164,'06','Buskerud',1),(164007,164,'07','Vestfold',1),(164008,164,'08','Telemark',1),(164009,164,'09','Aust-Agder',1),(164010,164,'10','Vest-Agder',1),(164011,164,'11','Rogaland',1),(164012,164,'12','Hordaland',1),(164013,164,'14','Sogn og Fjordane',1),(164014,164,'15','Møre of Romsdal',1),(164015,164,'16','Sør-Trøndelag',1),(164016,164,'17','Nord-Trøndelag',1),(164017,164,'18','Nordland',1),(164018,164,'19','Troms',1),(164019,164,'20','Finnmark',1),(202001,202,'AG','Aargau',1),(202002,202,'AR','Appenzell Ausserrhoden',1),(202003,202,'AI','Appenzell Innerrhoden',1),(202004,202,'BL','Basel-Landschaft',1),(202005,202,'BS','Basel-Stadt',1),(202006,202,'BE','Bern',1),(202007,202,'FR','Fribourg',1),(202008,202,'GE','Geneva',1),(202009,202,'GL','Glarus',1),(202010,202,'GR','Graubünden',1),(202011,202,'JU','Jura',1),(202012,202,'LU','Lucerne',1),(202013,202,'NE','Neuchâtel',1),(202014,202,'NW','Nidwalden',1),(202015,202,'OW','Obwalden',1),(202016,202,'SH','Schaffhausen',1),(202017,202,'SZ','Schwyz',1),(202018,202,'SO','Solothurn',1),(202019,202,'SG','St. Gallen',1),(202020,202,'TG','Thurgau',1),(202021,202,'TI','Ticino',1),(202022,202,'UR','Uri',1),(202023,202,'VS','Valais',1),(202024,202,'VD','Vaud',1),(202025,202,'ZG','Zug',1),(202026,202,'ZH','Zürich',1);
/*!40000 ALTER TABLE `Region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Release_`
--

DROP TABLE IF EXISTS `Release_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Release_` (
  `releaseId` bigint(20) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `servletContextName` varchar(75) DEFAULT NULL,
  `buildNumber` int(11) DEFAULT NULL,
  `buildDate` datetime DEFAULT NULL,
  `verified` tinyint(4) DEFAULT NULL,
  `state_` int(11) DEFAULT NULL,
  `testString` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`releaseId`),
  KEY `IX_8BD6BCA7` (`servletContextName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Release_`
--

LOCK TABLES `Release_` WRITE;
/*!40000 ALTER TABLE `Release_` DISABLE KEYS */;
INSERT INTO `Release_` VALUES (1,'2013-07-10 12:33:53','2013-07-10 10:34:07','portal',6101,'2013-07-10 10:34:07',1,0,'You take the blue pill, the story ends, you wake up in your bed and believe whatever you want to believe. You take the red pill, you stay in Wonderland, and I show you how deep the rabbit hole goes.');
/*!40000 ALTER TABLE `Release_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Repository`
--

DROP TABLE IF EXISTS `Repository`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Repository` (
  `uuid_` varchar(75) DEFAULT NULL,
  `repositoryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `portletId` varchar(75) DEFAULT NULL,
  `typeSettings` longtext,
  `dlFolderId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`repositoryId`),
  UNIQUE KEY `IX_11641E26` (`uuid_`,`groupId`),
  KEY `IX_5253B1FA` (`groupId`),
  KEY `IX_74C17B04` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Repository`
--

LOCK TABLES `Repository` WRITE;
/*!40000 ALTER TABLE `Repository` DISABLE KEYS */;
/*!40000 ALTER TABLE `Repository` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RepositoryEntry`
--

DROP TABLE IF EXISTS `RepositoryEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RepositoryEntry` (
  `uuid_` varchar(75) DEFAULT NULL,
  `repositoryEntryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `repositoryId` bigint(20) DEFAULT NULL,
  `mappedId` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`repositoryEntryId`),
  UNIQUE KEY `IX_9BDCF489` (`repositoryId`,`mappedId`),
  UNIQUE KEY `IX_354AA664` (`uuid_`,`groupId`),
  KEY `IX_B7034B27` (`repositoryId`),
  KEY `IX_B9B1506` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RepositoryEntry`
--

LOCK TABLES `RepositoryEntry` WRITE;
/*!40000 ALTER TABLE `RepositoryEntry` DISABLE KEYS */;
/*!40000 ALTER TABLE `RepositoryEntry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ResourceAction`
--

DROP TABLE IF EXISTS `ResourceAction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResourceAction` (
  `resourceActionId` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `actionId` varchar(75) DEFAULT NULL,
  `bitwiseValue` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`resourceActionId`),
  UNIQUE KEY `IX_EDB9986E` (`name`,`actionId`),
  KEY `IX_81F2DB09` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ResourceAction`
--

LOCK TABLES `ResourceAction` WRITE;
/*!40000 ALTER TABLE `ResourceAction` DISABLE KEYS */;
INSERT INTO `ResourceAction` VALUES (1,'com.liferay.portlet.softwarecatalog','ADD_FRAMEWORK_VERSION',2),(2,'com.liferay.portlet.softwarecatalog','ADD_PRODUCT_ENTRY',4),(3,'com.liferay.portlet.softwarecatalog','PERMISSIONS',8),(4,'com.liferay.portal.model.Team','ASSIGN_MEMBERS',2),(5,'com.liferay.portal.model.Team','DELETE',4),(6,'com.liferay.portal.model.Team','PERMISSIONS',8),(7,'com.liferay.portal.model.Team','UPDATE',16),(8,'com.liferay.portal.model.Team','VIEW',1),(9,'com.liferay.portal.model.PasswordPolicy','ASSIGN_MEMBERS',2),(10,'com.liferay.portal.model.PasswordPolicy','DELETE',4),(11,'com.liferay.portal.model.PasswordPolicy','PERMISSIONS',8),(12,'com.liferay.portal.model.PasswordPolicy','UPDATE',16),(13,'com.liferay.portal.model.PasswordPolicy','VIEW',1),(14,'com.liferay.portlet.blogs.model.BlogsEntry','ADD_DISCUSSION',2),(15,'com.liferay.portlet.blogs.model.BlogsEntry','DELETE',4),(16,'com.liferay.portlet.blogs.model.BlogsEntry','DELETE_DISCUSSION',8),(17,'com.liferay.portlet.blogs.model.BlogsEntry','PERMISSIONS',16),(18,'com.liferay.portlet.blogs.model.BlogsEntry','UPDATE',32),(19,'com.liferay.portlet.blogs.model.BlogsEntry','UPDATE_DISCUSSION',64),(20,'com.liferay.portlet.blogs.model.BlogsEntry','VIEW',1),(21,'com.liferay.portlet.dynamicdatamapping.model.DDMTemplate','DELETE',2),(22,'com.liferay.portlet.dynamicdatamapping.model.DDMTemplate','PERMISSIONS',4),(23,'com.liferay.portlet.dynamicdatamapping.model.DDMTemplate','UPDATE',8),(24,'com.liferay.portlet.dynamicdatamapping.model.DDMTemplate','VIEW',1),(25,'com.liferay.portlet.journal.model.JournalFeed','DELETE',2),(26,'com.liferay.portlet.journal.model.JournalFeed','PERMISSIONS',4),(27,'com.liferay.portlet.journal.model.JournalFeed','UPDATE',8),(28,'com.liferay.portlet.journal.model.JournalFeed','VIEW',1),(29,'com.liferay.portlet.wiki.model.WikiNode','ADD_ATTACHMENT',2),(30,'com.liferay.portlet.wiki.model.WikiNode','ADD_PAGE',4),(31,'com.liferay.portlet.wiki.model.WikiNode','DELETE',8),(32,'com.liferay.portlet.wiki.model.WikiNode','IMPORT',16),(33,'com.liferay.portlet.wiki.model.WikiNode','PERMISSIONS',32),(34,'com.liferay.portlet.wiki.model.WikiNode','SUBSCRIBE',64),(35,'com.liferay.portlet.wiki.model.WikiNode','UPDATE',128),(36,'com.liferay.portlet.wiki.model.WikiNode','VIEW',1),(37,'com.liferay.portlet.announcements.model.AnnouncementsEntry','DELETE',2),(38,'com.liferay.portlet.announcements.model.AnnouncementsEntry','UPDATE',4),(39,'com.liferay.portlet.announcements.model.AnnouncementsEntry','VIEW',1),(40,'com.liferay.portlet.announcements.model.AnnouncementsEntry','PERMISSIONS',8),(41,'com.liferay.portlet.bookmarks','ADD_ENTRY',2),(42,'com.liferay.portlet.bookmarks','ADD_FOLDER',4),(43,'com.liferay.portlet.bookmarks','PERMISSIONS',8),(44,'com.liferay.portlet.bookmarks','VIEW',1),(45,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance','DELETE',2),(46,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance','PERMISSIONS',4),(47,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance','UPDATE',8),(48,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance','VIEW',1),(49,'com.liferay.portlet.asset.model.AssetVocabulary','DELETE',2),(50,'com.liferay.portlet.asset.model.AssetVocabulary','PERMISSIONS',4),(51,'com.liferay.portlet.asset.model.AssetVocabulary','UPDATE',8),(52,'com.liferay.portlet.asset.model.AssetVocabulary','VIEW',1),(53,'com.liferay.portlet.documentlibrary.model.DLFolder','ACCESS',2),(54,'com.liferay.portlet.documentlibrary.model.DLFolder','ADD_DOCUMENT',4),(55,'com.liferay.portlet.documentlibrary.model.DLFolder','ADD_SHORTCUT',8),(56,'com.liferay.portlet.documentlibrary.model.DLFolder','ADD_SUBFOLDER',16),(57,'com.liferay.portlet.documentlibrary.model.DLFolder','DELETE',32),(58,'com.liferay.portlet.documentlibrary.model.DLFolder','PERMISSIONS',64),(59,'com.liferay.portlet.documentlibrary.model.DLFolder','UPDATE',128),(60,'com.liferay.portlet.documentlibrary.model.DLFolder','VIEW',1),(61,'com.liferay.portlet.expando.model.ExpandoColumn','DELETE',2),(62,'com.liferay.portlet.expando.model.ExpandoColumn','PERMISSIONS',4),(63,'com.liferay.portlet.expando.model.ExpandoColumn','UPDATE',8),(64,'com.liferay.portlet.expando.model.ExpandoColumn','VIEW',1),(65,'com.liferay.portlet.documentlibrary','ADD_DOCUMENT',2),(66,'com.liferay.portlet.documentlibrary','ADD_DOCUMENT_TYPE',4),(67,'com.liferay.portlet.documentlibrary','ADD_FOLDER',8),(68,'com.liferay.portlet.documentlibrary','ADD_REPOSITORY',16),(69,'com.liferay.portlet.documentlibrary','ADD_STRUCTURE',32),(70,'com.liferay.portlet.documentlibrary','ADD_SHORTCUT',64),(71,'com.liferay.portlet.documentlibrary','PERMISSIONS',128),(72,'com.liferay.portlet.documentlibrary','UPDATE',256),(73,'com.liferay.portlet.documentlibrary','VIEW',1),(74,'com.liferay.portlet.calendar.model.CalEvent','ADD_DISCUSSION',2),(75,'com.liferay.portlet.calendar.model.CalEvent','DELETE',4),(76,'com.liferay.portlet.calendar.model.CalEvent','DELETE_DISCUSSION',8),(77,'com.liferay.portlet.calendar.model.CalEvent','PERMISSIONS',16),(78,'com.liferay.portlet.calendar.model.CalEvent','UPDATE',32),(79,'com.liferay.portlet.calendar.model.CalEvent','UPDATE_DISCUSSION',64),(80,'com.liferay.portlet.calendar.model.CalEvent','VIEW',1),(81,'com.liferay.portlet.shopping.model.ShoppingCategory','ADD_ITEM',2),(82,'com.liferay.portlet.shopping.model.ShoppingCategory','ADD_SUBCATEGORY',4),(83,'com.liferay.portlet.shopping.model.ShoppingCategory','DELETE',8),(84,'com.liferay.portlet.shopping.model.ShoppingCategory','PERMISSIONS',16),(85,'com.liferay.portlet.shopping.model.ShoppingCategory','UPDATE',32),(86,'com.liferay.portlet.shopping.model.ShoppingCategory','VIEW',1),(87,'com.liferay.portlet.documentlibrary.model.DLFileShortcut','ADD_DISCUSSION',2),(88,'com.liferay.portlet.documentlibrary.model.DLFileShortcut','DELETE',4),(89,'com.liferay.portlet.documentlibrary.model.DLFileShortcut','DELETE_DISCUSSION',8),(90,'com.liferay.portlet.documentlibrary.model.DLFileShortcut','PERMISSIONS',16),(91,'com.liferay.portlet.documentlibrary.model.DLFileShortcut','UPDATE',32),(92,'com.liferay.portlet.documentlibrary.model.DLFileShortcut','UPDATE_DISCUSSION',64),(93,'com.liferay.portlet.documentlibrary.model.DLFileShortcut','VIEW',1),(94,'com.liferay.portlet.journal','ADD_ARTICLE',2),(95,'com.liferay.portlet.journal','ADD_FEED',4),(96,'com.liferay.portlet.journal','ADD_STRUCTURE',8),(97,'com.liferay.portlet.journal','ADD_TEMPLATE',16),(98,'com.liferay.portlet.journal','SUBSCRIBE',32),(99,'com.liferay.portlet.journal','PERMISSIONS',64),(100,'com.liferay.portlet.calendar','ADD_EVENT',2),(101,'com.liferay.portlet.calendar','EXPORT_ALL_EVENTS',4),(102,'com.liferay.portlet.calendar','PERMISSIONS',8),(103,'com.liferay.portal.model.LayoutPrototype','DELETE',2),(104,'com.liferay.portal.model.LayoutPrototype','PERMISSIONS',4),(105,'com.liferay.portal.model.LayoutPrototype','UPDATE',8),(106,'com.liferay.portal.model.LayoutPrototype','VIEW',1),(107,'com.liferay.portlet.dynamicdatalists.model.DDLRecordSet','ADD_RECORD',2),(108,'com.liferay.portlet.dynamicdatalists.model.DDLRecordSet','DELETE',4),(109,'com.liferay.portlet.dynamicdatalists.model.DDLRecordSet','PERMISSIONS',8),(110,'com.liferay.portlet.dynamicdatalists.model.DDLRecordSet','UPDATE',16),(111,'com.liferay.portlet.dynamicdatalists.model.DDLRecordSet','VIEW',1),(112,'com.liferay.portal.model.Organization','ASSIGN_MEMBERS',2),(113,'com.liferay.portal.model.Organization','ASSIGN_USER_ROLES',4),(114,'com.liferay.portal.model.Organization','DELETE',8),(115,'com.liferay.portal.model.Organization','MANAGE_ANNOUNCEMENTS',16),(116,'com.liferay.portal.model.Organization','MANAGE_SUBORGANIZATIONS',32),(117,'com.liferay.portal.model.Organization','MANAGE_USERS',64),(118,'com.liferay.portal.model.Organization','PERMISSIONS',128),(119,'com.liferay.portal.model.Organization','UPDATE',256),(120,'com.liferay.portal.model.Organization','VIEW',1),(121,'com.liferay.portlet.softwarecatalog.model.SCLicense','DELETE',2),(122,'com.liferay.portlet.softwarecatalog.model.SCLicense','PERMISSIONS',4),(123,'com.liferay.portlet.softwarecatalog.model.SCLicense','UPDATE',8),(124,'com.liferay.portlet.softwarecatalog.model.SCLicense','VIEW',1),(125,'com.liferay.portlet.documentlibrary.model.DLFileEntryType','DELETE',2),(126,'com.liferay.portlet.documentlibrary.model.DLFileEntryType','PERMISSIONS',4),(127,'com.liferay.portlet.documentlibrary.model.DLFileEntryType','UPDATE',8),(128,'com.liferay.portlet.documentlibrary.model.DLFileEntryType','VIEW',1),(129,'com.liferay.portlet.journal.model.JournalTemplate','DELETE',2),(130,'com.liferay.portlet.journal.model.JournalTemplate','PERMISSIONS',4),(131,'com.liferay.portlet.journal.model.JournalTemplate','UPDATE',8),(132,'com.liferay.portlet.journal.model.JournalTemplate','VIEW',1),(133,'com.liferay.portlet.journal.model.JournalArticle','ADD_DISCUSSION',2),(134,'com.liferay.portlet.journal.model.JournalArticle','DELETE',4),(135,'com.liferay.portlet.journal.model.JournalArticle','DELETE_DISCUSSION',8),(136,'com.liferay.portlet.journal.model.JournalArticle','EXPIRE',16),(137,'com.liferay.portlet.journal.model.JournalArticle','PERMISSIONS',32),(138,'com.liferay.portlet.journal.model.JournalArticle','UPDATE',64),(139,'com.liferay.portlet.journal.model.JournalArticle','UPDATE_DISCUSSION',128),(140,'com.liferay.portlet.journal.model.JournalArticle','VIEW',1),(141,'com.liferay.portlet.dynamicdatalists','ADD_RECORD_SET',2),(142,'com.liferay.portlet.dynamicdatalists','ADD_STRUCTURE',4),(143,'com.liferay.portlet.dynamicdatalists','ADD_TEMPLATE',8),(144,'com.liferay.portlet.dynamicdatalists','PERMISSIONS',16),(145,'com.liferay.portlet.bookmarks.model.BookmarksFolder','ACCESS',2),(146,'com.liferay.portlet.bookmarks.model.BookmarksFolder','ADD_ENTRY',4),(147,'com.liferay.portlet.bookmarks.model.BookmarksFolder','ADD_SUBFOLDER',8),(148,'com.liferay.portlet.bookmarks.model.BookmarksFolder','DELETE',16),(149,'com.liferay.portlet.bookmarks.model.BookmarksFolder','PERMISSIONS',32),(150,'com.liferay.portlet.bookmarks.model.BookmarksFolder','UPDATE',64),(151,'com.liferay.portlet.bookmarks.model.BookmarksFolder','VIEW',1),(152,'com.liferay.portal.model.Group','ADD_LAYOUT',2),(153,'com.liferay.portal.model.Group','ADD_LAYOUT_BRANCH',4),(154,'com.liferay.portal.model.Group','ADD_LAYOUT_SET_BRANCH',8),(155,'com.liferay.portal.model.Group','ASSIGN_MEMBERS',16),(156,'com.liferay.portal.model.Group','ASSIGN_USER_ROLES',32),(157,'com.liferay.portal.model.Group','CONFIGURE_PORTLETS',64),(158,'com.liferay.portal.model.Group','DELETE',128),(159,'com.liferay.portal.model.Group','EXPORT_IMPORT_LAYOUTS',256),(160,'com.liferay.portal.model.Group','EXPORT_IMPORT_PORTLET_INFO',512),(161,'com.liferay.portal.model.Group','MANAGE_ANNOUNCEMENTS',1024),(162,'com.liferay.portal.model.Group','MANAGE_ARCHIVED_SETUPS',2048),(163,'com.liferay.portal.model.Group','MANAGE_LAYOUTS',4096),(164,'com.liferay.portal.model.Group','MANAGE_STAGING',8192),(165,'com.liferay.portal.model.Group','MANAGE_TEAMS',16384),(166,'com.liferay.portal.model.Group','PERMISSIONS',32768),(167,'com.liferay.portal.model.Group','PUBLISH_STAGING',65536),(168,'com.liferay.portal.model.Group','PUBLISH_TO_REMOTE',131072),(169,'com.liferay.portal.model.Group','UPDATE',262144),(170,'com.liferay.portal.model.Group','VIEW',1),(171,'com.liferay.portal.model.Group','VIEW_MEMBERS',524288),(172,'com.liferay.portal.model.Group','VIEW_STAGING',1048576),(173,'com.liferay.portlet.journal.model.JournalStructure','DELETE',2),(174,'com.liferay.portlet.journal.model.JournalStructure','PERMISSIONS',4),(175,'com.liferay.portlet.journal.model.JournalStructure','UPDATE',8),(176,'com.liferay.portlet.journal.model.JournalStructure','VIEW',1),(177,'com.liferay.portlet.asset.model.AssetTag','DELETE',2),(178,'com.liferay.portlet.asset.model.AssetTag','PERMISSIONS',4),(179,'com.liferay.portlet.asset.model.AssetTag','UPDATE',8),(180,'com.liferay.portlet.asset.model.AssetTag','VIEW',1),(181,'com.liferay.portal.model.Layout','ADD_DISCUSSION',2),(182,'com.liferay.portal.model.Layout','ADD_LAYOUT',4),(183,'com.liferay.portal.model.Layout','CONFIGURE_PORTLETS',8),(184,'com.liferay.portal.model.Layout','CUSTOMIZE',16),(185,'com.liferay.portal.model.Layout','DELETE',32),(186,'com.liferay.portal.model.Layout','DELETE_DISCUSSION',64),(187,'com.liferay.portal.model.Layout','PERMISSIONS',128),(188,'com.liferay.portal.model.Layout','UPDATE',256),(189,'com.liferay.portal.model.Layout','UPDATE_DISCUSSION',512),(190,'com.liferay.portal.model.Layout','VIEW',1),(191,'com.liferay.portlet.asset','ADD_TAG',2),(192,'com.liferay.portlet.asset','PERMISSIONS',4),(193,'com.liferay.portlet.asset','ADD_CATEGORY',8),(194,'com.liferay.portlet.asset','ADD_VOCABULARY',16),(195,'com.liferay.portal.model.LayoutBranch','DELETE',2),(196,'com.liferay.portal.model.LayoutBranch','PERMISSIONS',4),(197,'com.liferay.portal.model.LayoutBranch','UPDATE',8),(198,'com.liferay.portal.model.LayoutSetBranch','DELETE',2),(199,'com.liferay.portal.model.LayoutSetBranch','MERGE',4),(200,'com.liferay.portal.model.LayoutSetBranch','PERMISSIONS',8),(201,'com.liferay.portal.model.LayoutSetBranch','UPDATE',16),(202,'com.liferay.portlet.messageboards','ADD_CATEGORY',2),(203,'com.liferay.portlet.messageboards','ADD_FILE',4),(204,'com.liferay.portlet.messageboards','ADD_MESSAGE',8),(205,'com.liferay.portlet.messageboards','BAN_USER',16),(206,'com.liferay.portlet.messageboards','MOVE_THREAD',32),(207,'com.liferay.portlet.messageboards','LOCK_THREAD',64),(208,'com.liferay.portlet.messageboards','PERMISSIONS',128),(209,'com.liferay.portlet.messageboards','REPLY_TO_MESSAGE',256),(210,'com.liferay.portlet.messageboards','SUBSCRIBE',512),(211,'com.liferay.portlet.messageboards','UPDATE_THREAD_PRIORITY',1024),(212,'com.liferay.portlet.messageboards','VIEW',1),(213,'com.liferay.portlet.polls','ADD_QUESTION',2),(214,'com.liferay.portlet.polls','PERMISSIONS',4),(215,'com.liferay.portlet.shopping.model.ShoppingItem','DELETE',2),(216,'com.liferay.portlet.shopping.model.ShoppingItem','PERMISSIONS',4),(217,'com.liferay.portlet.shopping.model.ShoppingItem','UPDATE',8),(218,'com.liferay.portlet.shopping.model.ShoppingItem','VIEW',1),(219,'com.liferay.portlet.bookmarks.model.BookmarksEntry','DELETE',2),(220,'com.liferay.portlet.bookmarks.model.BookmarksEntry','PERMISSIONS',4),(221,'com.liferay.portlet.bookmarks.model.BookmarksEntry','UPDATE',8),(222,'com.liferay.portlet.bookmarks.model.BookmarksEntry','VIEW',1),(223,'com.liferay.portlet.softwarecatalog.model.SCProductEntry','ADD_DISCUSSION',2),(224,'com.liferay.portlet.softwarecatalog.model.SCProductEntry','DELETE',4),(225,'com.liferay.portlet.softwarecatalog.model.SCProductEntry','DELETE_DISCUSSION',8),(226,'com.liferay.portlet.softwarecatalog.model.SCProductEntry','PERMISSIONS',16),(227,'com.liferay.portlet.softwarecatalog.model.SCProductEntry','UPDATE',32),(228,'com.liferay.portlet.softwarecatalog.model.SCProductEntry','UPDATE_DISCUSSION',64),(229,'com.liferay.portlet.softwarecatalog.model.SCProductEntry','VIEW',1),(230,'com.liferay.portal.model.User','DELETE',2),(231,'com.liferay.portal.model.User','IMPERSONATE',4),(232,'com.liferay.portal.model.User','PERMISSIONS',8),(233,'com.liferay.portal.model.User','UPDATE',16),(234,'com.liferay.portal.model.User','VIEW',1),(235,'com.liferay.portal.model.LayoutSetPrototype','DELETE',2),(236,'com.liferay.portal.model.LayoutSetPrototype','PERMISSIONS',4),(237,'com.liferay.portal.model.LayoutSetPrototype','UPDATE',8),(238,'com.liferay.portal.model.LayoutSetPrototype','VIEW',1),(239,'com.liferay.portlet.shopping','ADD_CATEGORY',2),(240,'com.liferay.portlet.shopping','ADD_ITEM',4),(241,'com.liferay.portlet.shopping','MANAGE_COUPONS',8),(242,'com.liferay.portlet.shopping','MANAGE_ORDERS',16),(243,'com.liferay.portlet.shopping','PERMISSIONS',32),(244,'com.liferay.portlet.shopping','VIEW',1),(245,'com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion','DELETE',2),(246,'com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion','PERMISSIONS',4),(247,'com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion','UPDATE',8),(248,'com.liferay.portlet.wiki','ADD_NODE',2),(249,'com.liferay.portlet.wiki','PERMISSIONS',4),(250,'com.liferay.portlet.polls.model.PollsQuestion','ADD_VOTE',2),(251,'com.liferay.portlet.polls.model.PollsQuestion','DELETE',4),(252,'com.liferay.portlet.polls.model.PollsQuestion','PERMISSIONS',8),(253,'com.liferay.portlet.polls.model.PollsQuestion','UPDATE',16),(254,'com.liferay.portlet.polls.model.PollsQuestion','VIEW',1),(255,'com.liferay.portlet.shopping.model.ShoppingOrder','DELETE',2),(256,'com.liferay.portlet.shopping.model.ShoppingOrder','PERMISSIONS',4),(257,'com.liferay.portlet.shopping.model.ShoppingOrder','UPDATE',8),(258,'com.liferay.portlet.shopping.model.ShoppingOrder','VIEW',1),(259,'com.liferay.portal.model.UserGroup','ASSIGN_MEMBERS',2),(260,'com.liferay.portal.model.UserGroup','DELETE',4),(261,'com.liferay.portal.model.UserGroup','MANAGE_ANNOUNCEMENTS',8),(262,'com.liferay.portal.model.UserGroup','PERMISSIONS',16),(263,'com.liferay.portal.model.UserGroup','UPDATE',32),(264,'com.liferay.portal.model.UserGroup','VIEW',1),(265,'com.liferay.portal.model.Role','ASSIGN_MEMBERS',2),(266,'com.liferay.portal.model.Role','DEFINE_PERMISSIONS',4),(267,'com.liferay.portal.model.Role','DELETE',8),(268,'com.liferay.portal.model.Role','MANAGE_ANNOUNCEMENTS',16),(269,'com.liferay.portal.model.Role','PERMISSIONS',32),(270,'com.liferay.portal.model.Role','UPDATE',64),(271,'com.liferay.portal.model.Role','VIEW',1),(272,'com.liferay.portlet.messageboards.model.MBCategory','ADD_FILE',2),(273,'com.liferay.portlet.messageboards.model.MBCategory','ADD_MESSAGE',4),(274,'com.liferay.portlet.messageboards.model.MBCategory','ADD_SUBCATEGORY',8),(275,'com.liferay.portlet.messageboards.model.MBCategory','DELETE',16),(276,'com.liferay.portlet.messageboards.model.MBCategory','LOCK_THREAD',32),(277,'com.liferay.portlet.messageboards.model.MBCategory','MOVE_THREAD',64),(278,'com.liferay.portlet.messageboards.model.MBCategory','PERMISSIONS',128),(279,'com.liferay.portlet.messageboards.model.MBCategory','REPLY_TO_MESSAGE',256),(280,'com.liferay.portlet.messageboards.model.MBCategory','SUBSCRIBE',512),(281,'com.liferay.portlet.messageboards.model.MBCategory','UPDATE',1024),(282,'com.liferay.portlet.messageboards.model.MBCategory','UPDATE_THREAD_PRIORITY',2048),(283,'com.liferay.portlet.messageboards.model.MBCategory','VIEW',1),(284,'com.liferay.portlet.mobiledevicerules','ADD_RULE_GROUP',2),(285,'com.liferay.portlet.mobiledevicerules','ADD_RULE_GROUP_INSTANCE',4),(286,'com.liferay.portlet.mobiledevicerules','CONFIGURATION',8),(287,'com.liferay.portlet.mobiledevicerules','VIEW',1),(288,'com.liferay.portlet.mobiledevicerules','PERMISSIONS',16),(289,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure','DELETE',2),(290,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure','PERMISSIONS',4),(291,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure','UPDATE',8),(292,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure','VIEW',1),(293,'com.liferay.portlet.wiki.model.WikiPage','ADD_DISCUSSION',2),(294,'com.liferay.portlet.wiki.model.WikiPage','DELETE',4),(295,'com.liferay.portlet.wiki.model.WikiPage','DELETE_DISCUSSION',8),(296,'com.liferay.portlet.wiki.model.WikiPage','PERMISSIONS',16),(297,'com.liferay.portlet.wiki.model.WikiPage','SUBSCRIBE',32),(298,'com.liferay.portlet.wiki.model.WikiPage','UPDATE',64),(299,'com.liferay.portlet.wiki.model.WikiPage','UPDATE_DISCUSSION',128),(300,'com.liferay.portlet.wiki.model.WikiPage','VIEW',1),(301,'com.liferay.portlet.asset.model.AssetCategory','ADD_CATEGORY',2),(302,'com.liferay.portlet.asset.model.AssetCategory','DELETE',4),(303,'com.liferay.portlet.asset.model.AssetCategory','PERMISSIONS',8),(304,'com.liferay.portlet.asset.model.AssetCategory','UPDATE',16),(305,'com.liferay.portlet.asset.model.AssetCategory','VIEW',1),(306,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup','DELETE',2),(307,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup','PERMISSIONS',4),(308,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup','UPDATE',8),(309,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup','VIEW',1),(310,'com.liferay.portlet.messageboards.model.MBMessage','DELETE',2),(311,'com.liferay.portlet.messageboards.model.MBMessage','PERMISSIONS',4),(312,'com.liferay.portlet.messageboards.model.MBMessage','SUBSCRIBE',8),(313,'com.liferay.portlet.messageboards.model.MBMessage','UPDATE',16),(314,'com.liferay.portlet.messageboards.model.MBMessage','VIEW',1),(315,'com.liferay.portlet.messageboards.model.MBThread','SUBSCRIBE',2),(316,'com.liferay.portlet.messageboards.model.MBThread','PERMISSIONS',4),(317,'com.liferay.portlet.blogs','ADD_ENTRY',2),(318,'com.liferay.portlet.blogs','PERMISSIONS',4),(319,'com.liferay.portlet.blogs','SUBSCRIBE',8),(320,'com.liferay.portlet.documentlibrary.model.DLFileEntry','ADD_DISCUSSION',2),(321,'com.liferay.portlet.documentlibrary.model.DLFileEntry','DELETE',4),(322,'com.liferay.portlet.documentlibrary.model.DLFileEntry','DELETE_DISCUSSION',8),(323,'com.liferay.portlet.documentlibrary.model.DLFileEntry','PERMISSIONS',16),(324,'com.liferay.portlet.documentlibrary.model.DLFileEntry','UPDATE',32),(325,'com.liferay.portlet.documentlibrary.model.DLFileEntry','UPDATE_DISCUSSION',64),(326,'com.liferay.portlet.documentlibrary.model.DLFileEntry','VIEW',1),(327,'98','ACCESS_IN_CONTROL_PANEL',2),(328,'98','ADD_TO_PAGE',4),(329,'98','CONFIGURATION',8),(330,'98','VIEW',1),(331,'98','PERMISSIONS',16),(332,'66','VIEW',1),(333,'66','ADD_TO_PAGE',2),(334,'66','CONFIGURATION',4),(335,'66','PERMISSIONS',8),(336,'156','VIEW',1),(337,'156','ADD_TO_PAGE',2),(338,'156','ACCESS_IN_CONTROL_PANEL',4),(339,'156','CONFIGURATION',8),(340,'156','PERMISSIONS',16),(341,'180','VIEW',1),(342,'180','ADD_TO_PAGE',2),(343,'180','CONFIGURATION',4),(344,'180','PERMISSIONS',8),(345,'152','ACCESS_IN_CONTROL_PANEL',2),(346,'152','CONFIGURATION',4),(347,'152','VIEW',1),(348,'152','PERMISSIONS',8),(349,'27','VIEW',1),(350,'27','ADD_TO_PAGE',2),(351,'27','CONFIGURATION',4),(352,'27','PERMISSIONS',8),(353,'88','VIEW',1),(354,'88','ADD_TO_PAGE',2),(355,'88','CONFIGURATION',4),(356,'88','PERMISSIONS',8),(357,'87','VIEW',1),(358,'87','ADD_TO_PAGE',2),(359,'87','CONFIGURATION',4),(360,'87','PERMISSIONS',8),(361,'134','ACCESS_IN_CONTROL_PANEL',2),(362,'134','CONFIGURATION',4),(363,'134','VIEW',1),(364,'134','PERMISSIONS',8),(365,'130','ACCESS_IN_CONTROL_PANEL',2),(366,'130','CONFIGURATION',4),(367,'130','VIEW',1),(368,'130','PERMISSIONS',8),(369,'122','VIEW',1),(370,'122','ADD_TO_PAGE',2),(371,'122','CONFIGURATION',4),(372,'122','PERMISSIONS',8),(373,'36','ADD_TO_PAGE',2),(374,'36','CONFIGURATION',4),(375,'36','VIEW',1),(376,'36','PERMISSIONS',8),(377,'26','VIEW',1),(378,'26','ADD_TO_PAGE',2),(379,'26','CONFIGURATION',4),(380,'26','PERMISSIONS',8),(381,'104','VIEW',1),(382,'104','ADD_TO_PAGE',2),(383,'104','ACCESS_IN_CONTROL_PANEL',4),(384,'104','CONFIGURATION',8),(385,'104','PERMISSIONS',16),(386,'175','VIEW',1),(387,'175','ADD_TO_PAGE',2),(388,'175','CONFIGURATION',4),(389,'175','PERMISSIONS',8),(390,'64','VIEW',1),(391,'64','ADD_TO_PAGE',2),(392,'64','CONFIGURATION',4),(393,'64','PERMISSIONS',8),(394,'153','ACCESS_IN_CONTROL_PANEL',2),(395,'153','ADD_TO_PAGE',4),(396,'153','CONFIGURATION',8),(397,'153','VIEW',1),(398,'153','PERMISSIONS',16),(399,'129','ACCESS_IN_CONTROL_PANEL',2),(400,'129','CONFIGURATION',4),(401,'129','VIEW',1),(402,'129','PERMISSIONS',8),(403,'179','VIEW',1),(404,'179','ADD_TO_PAGE',2),(405,'179','ACCESS_IN_CONTROL_PANEL',4),(406,'179','CONFIGURATION',8),(407,'179','PERMISSIONS',16),(408,'173','VIEW',1),(409,'173','ADD_TO_PAGE',2),(410,'173','ACCESS_IN_CONTROL_PANEL',4),(411,'173','CONFIGURATION',8),(412,'173','PERMISSIONS',16),(413,'100','VIEW',1),(414,'100','ADD_TO_PAGE',2),(415,'100','CONFIGURATION',4),(416,'100','PERMISSIONS',8),(417,'157','ACCESS_IN_CONTROL_PANEL',2),(418,'157','CONFIGURATION',4),(419,'157','VIEW',1),(420,'157','PERMISSIONS',8),(421,'19','ADD_TO_PAGE',2),(422,'19','CONFIGURATION',4),(423,'19','VIEW',1),(424,'19','PERMISSIONS',8),(425,'160','VIEW',1),(426,'160','ADD_TO_PAGE',2),(427,'160','CONFIGURATION',4),(428,'160','PERMISSIONS',8),(429,'128','ACCESS_IN_CONTROL_PANEL',2),(430,'128','CONFIGURATION',4),(431,'128','VIEW',1),(432,'128','PERMISSIONS',8),(433,'181','VIEW',1),(434,'181','ADD_TO_PAGE',2),(435,'181','CONFIGURATION',4),(436,'181','PERMISSIONS',8),(437,'86','VIEW',1),(438,'86','ADD_TO_PAGE',2),(439,'86','CONFIGURATION',4),(440,'86','PERMISSIONS',8),(441,'154','ACCESS_IN_CONTROL_PANEL',2),(442,'154','CONFIGURATION',4),(443,'154','VIEW',1),(444,'154','PERMISSIONS',8),(445,'148','VIEW',1),(446,'148','ADD_TO_PAGE',2),(447,'148','CONFIGURATION',4),(448,'148','PERMISSIONS',8),(449,'11','ADD_TO_PAGE',2),(450,'11','CONFIGURATION',4),(451,'11','VIEW',1),(452,'11','PERMISSIONS',8),(453,'29','ADD_TO_PAGE',2),(454,'29','CONFIGURATION',4),(455,'29','VIEW',1),(456,'29','PERMISSIONS',8),(457,'174','VIEW',1),(458,'174','ADD_TO_PAGE',2),(459,'174','ACCESS_IN_CONTROL_PANEL',4),(460,'174','CONFIGURATION',8),(461,'174','PERMISSIONS',16),(462,'158','ACCESS_IN_CONTROL_PANEL',2),(463,'158','ADD_TO_PAGE',4),(464,'158','CONFIGURATION',8),(465,'158','VIEW',1),(466,'158','PERMISSIONS',16),(467,'178','VIEW',1),(468,'178','ADD_TO_PAGE',2),(469,'178','ACCESS_IN_CONTROL_PANEL',4),(470,'178','CONFIGURATION',8),(471,'178','PERMISSIONS',16),(472,'124','VIEW',1),(473,'124','ADD_TO_PAGE',2),(474,'124','CONFIGURATION',4),(475,'124','PERMISSIONS',8),(476,'8','ACCESS_IN_CONTROL_PANEL',2),(477,'8','ADD_TO_PAGE',4),(478,'8','CONFIGURATION',8),(479,'8','VIEW',1),(480,'8','PERMISSIONS',16),(481,'58','ADD_TO_PAGE',2),(482,'58','CONFIGURATION',4),(483,'58','VIEW',1),(484,'58','PERMISSIONS',8),(485,'97','VIEW',1),(486,'97','ADD_TO_PAGE',2),(487,'97','CONFIGURATION',4),(488,'97','PERMISSIONS',8),(489,'71','ADD_TO_PAGE',2),(490,'71','CONFIGURATION',4),(491,'71','VIEW',1),(492,'71','PERMISSIONS',8),(493,'39','VIEW',1),(494,'39','ADD_TO_PAGE',2),(495,'39','CONFIGURATION',4),(496,'39','PERMISSIONS',8),(497,'177','CONFIGURATION',2),(498,'177','VIEW',1),(499,'177','ADD_TO_PAGE',4),(500,'177','PERMISSIONS',8),(501,'177','ACCESS_IN_CONTROL_PANEL',16),(502,'170','VIEW',1),(503,'170','ADD_TO_PAGE',2),(504,'170','CONFIGURATION',4),(505,'170','PERMISSIONS',8),(506,'85','ADD_TO_PAGE',2),(507,'85','CONFIGURATION',4),(508,'85','VIEW',1),(509,'85','PERMISSIONS',8),(510,'118','VIEW',1),(511,'118','ADD_TO_PAGE',2),(512,'118','CONFIGURATION',4),(513,'118','PERMISSIONS',8),(514,'107','VIEW',1),(515,'107','ADD_TO_PAGE',2),(516,'107','CONFIGURATION',4),(517,'107','PERMISSIONS',8),(518,'30','VIEW',1),(519,'30','ADD_TO_PAGE',2),(520,'30','CONFIGURATION',4),(521,'30','PERMISSIONS',8),(522,'147','ACCESS_IN_CONTROL_PANEL',2),(523,'147','CONFIGURATION',4),(524,'147','VIEW',1),(525,'147','PERMISSIONS',8),(526,'48','VIEW',1),(527,'48','ADD_TO_PAGE',2),(528,'48','CONFIGURATION',4),(529,'48','PERMISSIONS',8),(530,'125','ACCESS_IN_CONTROL_PANEL',2),(531,'125','CONFIGURATION',4),(532,'125','EXPORT_USER',8),(533,'125','VIEW',1),(534,'125','PERMISSIONS',16),(535,'161','ACCESS_IN_CONTROL_PANEL',2),(536,'161','CONFIGURATION',4),(537,'161','VIEW',1),(538,'161','PERMISSIONS',8),(539,'144','VIEW',1),(540,'144','ADD_TO_PAGE',2),(541,'144','CONFIGURATION',4),(542,'144','PERMISSIONS',8),(543,'146','ACCESS_IN_CONTROL_PANEL',2),(544,'146','CONFIGURATION',4),(545,'146','VIEW',1),(546,'146','PERMISSIONS',8),(547,'62','VIEW',1),(548,'62','ADD_TO_PAGE',2),(549,'62','CONFIGURATION',4),(550,'62','PERMISSIONS',8),(551,'162','ACCESS_IN_CONTROL_PANEL',2),(552,'162','CONFIGURATION',4),(553,'162','VIEW',1),(554,'162','PERMISSIONS',8),(555,'176','VIEW',1),(556,'176','ADD_TO_PAGE',2),(557,'176','ACCESS_IN_CONTROL_PANEL',4),(558,'176','CONFIGURATION',8),(559,'176','PERMISSIONS',16),(560,'172','VIEW',1),(561,'172','ADD_TO_PAGE',2),(562,'172','CONFIGURATION',4),(563,'172','PERMISSIONS',8),(564,'108','VIEW',1),(565,'108','ADD_TO_PAGE',2),(566,'108','CONFIGURATION',4),(567,'108','PERMISSIONS',8),(568,'139','ACCESS_IN_CONTROL_PANEL',2),(569,'139','ADD_EXPANDO',4),(570,'139','CONFIGURATION',8),(571,'139','VIEW',1),(572,'139','PERMISSIONS',16),(573,'84','ADD_ENTRY',2),(574,'84','ADD_TO_PAGE',4),(575,'84','CONFIGURATION',8),(576,'84','VIEW',1),(577,'84','PERMISSIONS',16),(578,'101','VIEW',1),(579,'101','ADD_TO_PAGE',2),(580,'101','CONFIGURATION',4),(581,'101','PERMISSIONS',8),(582,'121','VIEW',1),(583,'121','ADD_TO_PAGE',2),(584,'121','CONFIGURATION',4),(585,'121','PERMISSIONS',8),(586,'49','VIEW',1),(587,'49','ADD_TO_PAGE',2),(588,'49','CONFIGURATION',4),(589,'49','PERMISSIONS',8),(590,'143','VIEW',1),(591,'143','ADD_TO_PAGE',2),(592,'143','CONFIGURATION',4),(593,'143','PERMISSIONS',8),(594,'167','ACCESS_IN_CONTROL_PANEL',2),(595,'167','ADD_TO_PAGE',4),(596,'167','CONFIGURATION',8),(597,'167','VIEW',1),(598,'167','PERMISSIONS',16),(599,'77','VIEW',1),(600,'77','ADD_TO_PAGE',2),(601,'77','CONFIGURATION',4),(602,'77','PERMISSIONS',8),(603,'115','VIEW',1),(604,'115','ADD_TO_PAGE',2),(605,'115','CONFIGURATION',4),(606,'115','PERMISSIONS',8),(607,'56','ADD_TO_PAGE',2),(608,'56','CONFIGURATION',4),(609,'56','VIEW',1),(610,'56','PERMISSIONS',8),(611,'142','VIEW',1),(612,'142','ADD_TO_PAGE',2),(613,'142','CONFIGURATION',4),(614,'142','PERMISSIONS',8),(615,'111','VIEW',1),(616,'111','ADD_TO_PAGE',2),(617,'111','CONFIGURATION',4),(618,'111','PERMISSIONS',8),(619,'16','PREFERENCES',2),(620,'16','GUEST_PREFERENCES',4),(621,'16','VIEW',1),(622,'16','ADD_TO_PAGE',8),(623,'16','CONFIGURATION',16),(624,'16','PERMISSIONS',32),(625,'3','VIEW',1),(626,'3','ADD_TO_PAGE',2),(627,'3','CONFIGURATION',4),(628,'3','PERMISSIONS',8),(629,'20','ACCESS_IN_CONTROL_PANEL',2),(630,'20','ADD_TO_PAGE',4),(631,'20','CONFIGURATION',8),(632,'20','VIEW',1),(633,'20','PERMISSIONS',16),(634,'23','VIEW',1),(635,'23','ADD_TO_PAGE',2),(636,'23','CONFIGURATION',4),(637,'23','PERMISSIONS',8),(638,'145','VIEW',1),(639,'145','ADD_TO_PAGE',2),(640,'145','CONFIGURATION',4),(641,'145','PERMISSIONS',8),(642,'83','ADD_ENTRY',2),(643,'83','ADD_TO_PAGE',4),(644,'83','CONFIGURATION',8),(645,'83','VIEW',1),(646,'83','PERMISSIONS',16),(647,'99','ACCESS_IN_CONTROL_PANEL',2),(648,'99','CONFIGURATION',4),(649,'99','VIEW',1),(650,'99','PERMISSIONS',8),(651,'70','VIEW',1),(652,'70','ADD_TO_PAGE',2),(653,'70','CONFIGURATION',4),(654,'70','PERMISSIONS',8),(655,'164','VIEW',1),(656,'164','ADD_TO_PAGE',2),(657,'164','CONFIGURATION',4),(658,'164','PERMISSIONS',8),(659,'141','VIEW',1),(660,'141','ADD_TO_PAGE',2),(661,'141','CONFIGURATION',4),(662,'141','PERMISSIONS',8),(663,'9','VIEW',1),(664,'9','ADD_TO_PAGE',2),(665,'9','CONFIGURATION',4),(666,'9','PERMISSIONS',8),(667,'137','ACCESS_IN_CONTROL_PANEL',2),(668,'137','CONFIGURATION',4),(669,'137','VIEW',1),(670,'137','PERMISSIONS',8),(671,'28','ACCESS_IN_CONTROL_PANEL',2),(672,'28','ADD_TO_PAGE',4),(673,'28','CONFIGURATION',8),(674,'28','VIEW',1),(675,'28','PERMISSIONS',16),(676,'133','VIEW',1),(677,'133','ADD_TO_PAGE',2),(678,'133','CONFIGURATION',4),(679,'133','PERMISSIONS',8),(680,'116','VIEW',1),(681,'116','ADD_TO_PAGE',2),(682,'116','CONFIGURATION',4),(683,'116','PERMISSIONS',8),(684,'15','ACCESS_IN_CONTROL_PANEL',2),(685,'15','ADD_TO_PAGE',4),(686,'15','CONFIGURATION',8),(687,'15','VIEW',1),(688,'15','PERMISSIONS',16),(689,'47','VIEW',1),(690,'47','ADD_TO_PAGE',2),(691,'47','CONFIGURATION',4),(692,'47','PERMISSIONS',8),(693,'82','VIEW',1),(694,'82','ADD_TO_PAGE',2),(695,'82','CONFIGURATION',4),(696,'82','PERMISSIONS',8),(697,'103','VIEW',1),(698,'103','ADD_TO_PAGE',2),(699,'103','CONFIGURATION',4),(700,'103','PERMISSIONS',8),(701,'151','ACCESS_IN_CONTROL_PANEL',2),(702,'151','CONFIGURATION',4),(703,'151','VIEW',1),(704,'151','PERMISSIONS',8),(705,'140','ACCESS_IN_CONTROL_PANEL',2),(706,'140','CONFIGURATION',4),(707,'140','VIEW',1),(708,'140','PERMISSIONS',8),(709,'54','VIEW',1),(710,'54','ADD_TO_PAGE',2),(711,'54','CONFIGURATION',4),(712,'54','PERMISSIONS',8),(713,'169','VIEW',1),(714,'169','ADD_TO_PAGE',2),(715,'169','CONFIGURATION',4),(716,'169','PERMISSIONS',8),(717,'132','ACCESS_IN_CONTROL_PANEL',2),(718,'132','CONFIGURATION',4),(719,'132','VIEW',1),(720,'132','PERMISSIONS',8),(721,'34','ADD_TO_PAGE',2),(722,'34','CONFIGURATION',4),(723,'34','VIEW',1),(724,'34','PERMISSIONS',8),(725,'61','VIEW',1),(726,'61','ADD_TO_PAGE',2),(727,'61','CONFIGURATION',4),(728,'61','PERMISSIONS',8),(729,'73','ADD_TO_PAGE',2),(730,'73','CONFIGURATION',4),(731,'73','VIEW',1),(732,'73','PERMISSIONS',8),(733,'31','VIEW',1),(734,'31','ADD_TO_PAGE',2),(735,'31','CONFIGURATION',4),(736,'31','PERMISSIONS',8),(737,'165','VIEW',1),(738,'165','ADD_TO_PAGE',2),(739,'165','ACCESS_IN_CONTROL_PANEL',4),(740,'165','CONFIGURATION',8),(741,'165','PERMISSIONS',16),(742,'136','ACCESS_IN_CONTROL_PANEL',2),(743,'136','CONFIGURATION',4),(744,'136','VIEW',1),(745,'136','PERMISSIONS',8),(746,'127','VIEW',1),(747,'127','ADD_TO_PAGE',2),(748,'127','ACCESS_IN_CONTROL_PANEL',4),(749,'127','CONFIGURATION',8),(750,'127','PERMISSIONS',16),(751,'50','VIEW',1),(752,'50','ADD_TO_PAGE',2),(753,'50','CONFIGURATION',4),(754,'50','PERMISSIONS',8),(755,'25','ACCESS_IN_CONTROL_PANEL',2),(756,'25','CONFIGURATION',4),(757,'25','VIEW',1),(758,'25','PERMISSIONS',8),(759,'166','ACCESS_IN_CONTROL_PANEL',2),(760,'166','ADD_TO_PAGE',4),(761,'166','CONFIGURATION',8),(762,'166','VIEW',1),(763,'166','PERMISSIONS',16),(764,'90','ADD_COMMUNITY',2),(765,'90','ADD_LAYOUT_PROTOTYPE',4),(766,'90','ADD_LAYOUT_SET_PROTOTYPE',8),(767,'90','ADD_LICENSE',16),(768,'90','ADD_ORGANIZATION',32),(769,'90','ADD_PASSWORD_POLICY',64),(770,'90','ADD_ROLE',128),(771,'90','ADD_USER',256),(772,'90','ADD_USER_GROUP',512),(773,'90','CONFIGURATION',1024),(774,'90','EXPORT_USER',2048),(775,'90','IMPERSONATE',4096),(776,'90','UNLINK_LAYOUT_SET_PROTOTYPE',8192),(777,'90','VIEW_CONTROL_PANEL',16384),(778,'90','ADD_TO_PAGE',32768),(779,'90','PERMISSIONS',65536),(780,'90','VIEW',1),(781,'150','ACCESS_IN_CONTROL_PANEL',2),(782,'150','CONFIGURATION',4),(783,'150','VIEW',1),(784,'150','PERMISSIONS',8),(785,'113','VIEW',1),(786,'113','ADD_TO_PAGE',2),(787,'113','CONFIGURATION',4),(788,'113','PERMISSIONS',8),(789,'33','ADD_TO_PAGE',2),(790,'33','CONFIGURATION',4),(791,'33','VIEW',1),(792,'33','PERMISSIONS',8),(793,'2','ACCESS_IN_CONTROL_PANEL',2),(794,'2','CONFIGURATION',4),(795,'2','VIEW',1),(796,'2','PERMISSIONS',8),(797,'119','VIEW',1),(798,'119','ADD_TO_PAGE',2),(799,'119','CONFIGURATION',4),(800,'119','PERMISSIONS',8),(801,'114','VIEW',1),(802,'114','ADD_TO_PAGE',2),(803,'114','CONFIGURATION',4),(804,'114','PERMISSIONS',8),(805,'149','ACCESS_IN_CONTROL_PANEL',2),(806,'149','CONFIGURATION',4),(807,'149','VIEW',1),(808,'149','PERMISSIONS',8),(809,'67','VIEW',1),(810,'67','ADD_TO_PAGE',2),(811,'67','CONFIGURATION',4),(812,'67','PERMISSIONS',8),(813,'110','VIEW',1),(814,'110','ADD_TO_PAGE',2),(815,'110','CONFIGURATION',4),(816,'110','PERMISSIONS',8),(817,'135','ACCESS_IN_CONTROL_PANEL',2),(818,'135','CONFIGURATION',4),(819,'135','VIEW',1),(820,'135','PERMISSIONS',8),(821,'59','VIEW',1),(822,'59','ADD_TO_PAGE',2),(823,'59','CONFIGURATION',4),(824,'59','PERMISSIONS',8),(825,'131','ACCESS_IN_CONTROL_PANEL',2),(826,'131','CONFIGURATION',4),(827,'131','VIEW',1),(828,'131','PERMISSIONS',8),(829,'102','VIEW',1),(830,'102','ADD_TO_PAGE',2),(831,'102','CONFIGURATION',4),(832,'102','PERMISSIONS',8);
/*!40000 ALTER TABLE `ResourceAction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ResourceBlock`
--

DROP TABLE IF EXISTS `ResourceBlock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResourceBlock` (
  `resourceBlockId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `permissionsHash` varchar(75) DEFAULT NULL,
  `referenceCount` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`resourceBlockId`),
  UNIQUE KEY `IX_AEEA209C` (`companyId`,`groupId`,`name`,`permissionsHash`),
  KEY `IX_DA30B086` (`companyId`,`groupId`,`name`),
  KEY `IX_2D4CC782` (`companyId`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ResourceBlock`
--

LOCK TABLES `ResourceBlock` WRITE;
/*!40000 ALTER TABLE `ResourceBlock` DISABLE KEYS */;
/*!40000 ALTER TABLE `ResourceBlock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ResourceBlockPermission`
--

DROP TABLE IF EXISTS `ResourceBlockPermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResourceBlockPermission` (
  `resourceBlockPermissionId` bigint(20) NOT NULL,
  `resourceBlockId` bigint(20) DEFAULT NULL,
  `roleId` bigint(20) DEFAULT NULL,
  `actionIds` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`resourceBlockPermissionId`),
  UNIQUE KEY `IX_D63D20BB` (`resourceBlockId`,`roleId`),
  KEY `IX_4AB3756` (`resourceBlockId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ResourceBlockPermission`
--

LOCK TABLES `ResourceBlockPermission` WRITE;
/*!40000 ALTER TABLE `ResourceBlockPermission` DISABLE KEYS */;
/*!40000 ALTER TABLE `ResourceBlockPermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ResourceCode`
--

DROP TABLE IF EXISTS `ResourceCode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResourceCode` (
  `codeId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `scope` int(11) DEFAULT NULL,
  PRIMARY KEY (`codeId`),
  UNIQUE KEY `IX_A32C097E` (`companyId`,`name`,`scope`),
  KEY `IX_717FDD47` (`companyId`),
  KEY `IX_AACAFF40` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ResourceCode`
--

LOCK TABLES `ResourceCode` WRITE;
/*!40000 ALTER TABLE `ResourceCode` DISABLE KEYS */;
/*!40000 ALTER TABLE `ResourceCode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ResourcePermission`
--

DROP TABLE IF EXISTS `ResourcePermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResourcePermission` (
  `resourcePermissionId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `scope` int(11) DEFAULT NULL,
  `primKey` varchar(255) DEFAULT NULL,
  `roleId` bigint(20) DEFAULT NULL,
  `ownerId` bigint(20) DEFAULT NULL,
  `actionIds` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`resourcePermissionId`),
  KEY `IX_88705859` (`companyId`,`name`,`primKey`,`ownerId`),
  KEY `IX_C94C7708` (`companyId`,`name`,`primKey`,`roleId`,`actionIds`),
  KEY `IX_60B99860` (`companyId`,`name`,`scope`),
  KEY `IX_2200AA69` (`companyId`,`name`,`scope`,`primKey`),
  KEY `IX_8D83D0CE` (`companyId`,`name`,`scope`,`primKey`,`roleId`),
  KEY `IX_D2E2B644` (`companyId`,`name`,`scope`,`primKey`,`roleId`,`actionIds`),
  KEY `IX_4A1F4402` (`companyId`,`name`,`scope`,`primKey`,`roleId`,`ownerId`,`actionIds`),
  KEY `IX_26284944` (`companyId`,`primKey`),
  KEY `IX_A37A0588` (`roleId`),
  KEY `IX_F4555981` (`scope`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ResourcePermission`
--

LOCK TABLES `ResourcePermission` WRITE;
/*!40000 ALTER TABLE `ResourcePermission` DISABLE KEYS */;
INSERT INTO `ResourcePermission` VALUES (164,10154,'100',1,'10154',10164,0,2),(165,10154,'100',1,'10154',10165,0,2),(29,10154,'100',2,'10189',10165,0,1),(214,10154,'101',1,'10154',10162,0,2),(215,10154,'101',1,'10154',10164,0,2),(216,10154,'101',1,'10154',10165,0,2),(60,10154,'101',2,'10189',10165,0,1),(344,10154,'101',4,'10323_LAYOUT_101_INSTANCE_1UT6mxxw3qAs',10162,0,1),(342,10154,'101',4,'10323_LAYOUT_101_INSTANCE_1UT6mxxw3qAs',10163,0,15),(343,10154,'101',4,'10323_LAYOUT_101_INSTANCE_1UT6mxxw3qAs',10170,0,1),(301,10154,'102',1,'10154',10162,0,2),(302,10154,'102',1,'10154',10164,0,2),(303,10154,'102',1,'10154',10165,0,2),(106,10154,'102',2,'10189',10165,0,1),(152,10154,'104',1,'10154',10161,0,2),(22,10154,'104',2,'10189',10165,0,1),(201,10154,'107',1,'10154',10164,0,2),(202,10154,'107',1,'10154',10165,0,2),(48,10154,'107',2,'10189',10165,0,1),(210,10154,'108',1,'10154',10164,0,2),(211,10154,'108',1,'10154',10165,0,2),(58,10154,'108',2,'10189',10165,0,1),(174,10154,'11',1,'10154',10164,0,2),(175,10154,'11',1,'10154',10165,0,2),(36,10154,'11',2,'10189',10165,0,1),(297,10154,'110',1,'10154',10164,0,2),(298,10154,'110',1,'10154',10165,0,2),(102,10154,'110',2,'10189',10165,0,1),(236,10154,'111',1,'10154',10161,0,2),(68,10154,'111',2,'10189',10165,0,1),(292,10154,'114',1,'10154',10162,0,2),(293,10154,'114',1,'10154',10164,0,2),(294,10154,'114',1,'10154',10165,0,2),(99,10154,'114',2,'10189',10165,0,1),(329,10154,'114',4,'10313_LAYOUT_114',10162,0,1),(327,10154,'114',4,'10313_LAYOUT_114',10163,0,15),(328,10154,'114',4,'10313_LAYOUT_114',10170,0,1),(228,10154,'115',1,'10154',10162,0,2),(229,10154,'115',1,'10154',10164,0,2),(230,10154,'115',1,'10154',10165,0,2),(65,10154,'115',2,'10189',10165,0,1),(263,10154,'116',1,'10154',10162,0,2),(264,10154,'116',1,'10154',10164,0,2),(265,10154,'116',1,'10154',10165,0,2),(82,10154,'116',2,'10189',10165,0,1),(198,10154,'118',1,'10154',10162,0,2),(199,10154,'118',1,'10154',10164,0,2),(200,10154,'118',1,'10154',10165,0,2),(47,10154,'118',2,'10189',10165,0,1),(217,10154,'121',1,'10154',10162,0,2),(218,10154,'121',1,'10154',10164,0,2),(219,10154,'121',1,'10154',10165,0,2),(61,10154,'121',2,'10189',10165,0,1),(145,10154,'122',1,'10154',10162,0,2),(146,10154,'122',1,'10154',10164,0,2),(147,10154,'122',1,'10154',10165,0,2),(19,10154,'122',2,'10189',10165,0,1),(338,10154,'122',4,'10323_LAYOUT_122_INSTANCE_qqTARFxOfv8B',10162,0,1),(336,10154,'122',4,'10323_LAYOUT_122_INSTANCE_qqTARFxOfv8B',10163,0,15),(337,10154,'122',4,'10323_LAYOUT_122_INSTANCE_qqTARFxOfv8B',10170,0,1),(354,10154,'122',4,'10332_LAYOUT_122_INSTANCE_vwqJJu9YUePo',10162,0,1),(352,10154,'122',4,'10332_LAYOUT_122_INSTANCE_vwqJJu9YUePo',10163,0,15),(353,10154,'122',4,'10332_LAYOUT_122_INSTANCE_vwqJJu9YUePo',10170,0,1),(52,10154,'125',2,'10189',10165,0,1),(286,10154,'127',1,'10154',10161,0,2),(94,10154,'127',2,'10189',10165,0,1),(32,10154,'128',2,'10189',10165,0,1),(26,10154,'129',2,'10189',10165,0,1),(18,10154,'130',2,'10189',10165,0,1),(105,10154,'131',2,'10189',10165,0,1),(88,10154,'132',2,'10189',10165,0,1),(17,10154,'134',2,'10189',10165,0,1),(104,10154,'135',2,'10189',10165,0,1),(92,10154,'136',2,'10189',10165,0,1),(79,10154,'137',2,'10189',10165,0,1),(2,10154,'140',1,'10154',10165,0,2),(252,10154,'141',1,'10154',10162,0,2),(253,10154,'141',1,'10154',10164,0,2),(254,10154,'141',1,'10154',10165,0,2),(76,10154,'141',2,'10189',10165,0,1),(335,10154,'141',4,'10323_LAYOUT_141_INSTANCE_fWpN7YyLYuRA',10162,0,1),(333,10154,'141',4,'10323_LAYOUT_141_INSTANCE_fWpN7YyLYuRA',10163,0,15),(334,10154,'141',4,'10323_LAYOUT_141_INSTANCE_fWpN7YyLYuRA',10170,0,1),(357,10154,'141',4,'10332_LAYOUT_141_INSTANCE_B0Ccx2TXMdw7',10162,0,1),(355,10154,'141',4,'10332_LAYOUT_141_INSTANCE_B0Ccx2TXMdw7',10163,0,15),(356,10154,'141',4,'10332_LAYOUT_141_INSTANCE_B0Ccx2TXMdw7',10170,0,1),(220,10154,'143',1,'10154',10162,0,2),(221,10154,'143',1,'10154',10164,0,2),(222,10154,'143',1,'10154',10165,0,2),(62,10154,'143',2,'10189',10165,0,1),(54,10154,'146',2,'10189',10165,0,1),(117,10154,'147',2,'10189',10164,0,2),(50,10154,'147',2,'10189',10165,0,1),(171,10154,'148',1,'10154',10162,0,2),(172,10154,'148',1,'10154',10164,0,2),(173,10154,'148',1,'10154',10165,0,2),(35,10154,'148',2,'10189',10165,0,1),(326,10154,'148',4,'10313_LAYOUT_148_INSTANCE_25GFqsluNMC2',10162,0,1),(324,10154,'148',4,'10313_LAYOUT_148_INSTANCE_25GFqsluNMC2',10163,0,15),(325,10154,'148',4,'10313_LAYOUT_148_INSTANCE_25GFqsluNMC2',10170,0,1),(100,10154,'149',2,'10189',10165,0,1),(261,10154,'15',1,'10154',10164,0,4),(262,10154,'15',1,'10154',10165,0,4),(124,10154,'15',2,'10189',10164,0,2),(81,10154,'15',2,'10189',10165,0,1),(98,10154,'150',2,'10189',10165,0,1),(84,10154,'151',2,'10189',10165,0,1),(111,10154,'152',2,'10189',10164,0,2),(16,10154,'152',2,'10189',10165,0,1),(156,10154,'153',1,'10154',10164,0,4),(4,10154,'153',1,'10154',10165,0,6),(24,10154,'153',2,'10189',10165,0,1),(114,10154,'154',2,'10189',10164,0,2),(34,10154,'154',2,'10189',10165,0,1),(31,10154,'157',2,'10189',10165,0,1),(178,10154,'158',1,'10154',10164,0,4),(3,10154,'158',1,'10154',10165,0,6),(38,10154,'158',2,'10189',10165,0,1),(234,10154,'16',1,'10154',10164,0,8),(235,10154,'16',1,'10154',10165,0,8),(67,10154,'16',2,'10189',10165,0,1),(118,10154,'161',2,'10189',10164,0,2),(53,10154,'161',2,'10189',10165,0,1),(119,10154,'162',2,'10189',10164,0,2),(56,10154,'162',2,'10189',10165,0,1),(247,10154,'164',1,'10154',10162,0,2),(248,10154,'164',1,'10154',10164,0,2),(249,10154,'164',1,'10154',10165,0,2),(73,10154,'164',2,'10189',10165,0,1),(287,10154,'166',1,'10154',10164,0,4),(288,10154,'166',1,'10154',10165,0,4),(96,10154,'166',2,'10189',10165,0,1),(226,10154,'167',1,'10154',10164,0,4),(227,10154,'167',1,'10154',10165,0,4),(120,10154,'167',2,'10189',10164,0,2),(64,10154,'167',2,'10189',10165,0,1),(273,10154,'169',1,'10154',10164,0,2),(274,10154,'169',1,'10154',10165,0,2),(87,10154,'169',2,'10189',10165,0,1),(161,10154,'173',1,'10154',10162,0,2),(162,10154,'173',1,'10154',10164,0,2),(163,10154,'173',1,'10154',10165,0,2),(113,10154,'173',2,'10189',10164,0,4),(28,10154,'173',2,'10189',10165,0,1),(153,10154,'175',1,'10154',10162,0,2),(154,10154,'175',1,'10154',10164,0,2),(155,10154,'175',1,'10154',10165,0,2),(23,10154,'175',2,'10189',10165,0,1),(209,10154,'176',1,'10154',10161,0,2),(57,10154,'176',2,'10189',10165,0,1),(193,10154,'177',1,'10154',10164,0,4),(194,10154,'177',1,'10154',10165,0,4),(45,10154,'177',2,'10189',10165,0,1),(179,10154,'178',1,'10154',10164,0,2),(180,10154,'178',1,'10154',10165,0,2),(115,10154,'178',2,'10189',10164,0,4),(39,10154,'178',2,'10189',10165,0,1),(160,10154,'179',1,'10154',10161,0,2),(112,10154,'179',2,'10189',10164,0,4),(27,10154,'179',2,'10189',10165,0,1),(140,10154,'180',1,'10154',10162,0,2),(141,10154,'180',1,'10154',10164,0,2),(142,10154,'180',1,'10154',10165,0,2),(14,10154,'180',2,'10189',10165,0,1),(168,10154,'181',1,'10154',10162,0,2),(169,10154,'181',1,'10154',10164,0,2),(170,10154,'181',1,'10154',10165,0,2),(33,10154,'181',2,'10189',10165,0,1),(166,10154,'19',1,'10154',10164,0,2),(167,10154,'19',1,'10154',10165,0,2),(30,10154,'19',2,'10189',10165,0,1),(1,10154,'2',1,'10154',10165,0,2),(242,10154,'20',1,'10154',10162,0,4),(243,10154,'20',1,'10154',10164,0,4),(244,10154,'20',1,'10154',10165,0,4),(121,10154,'20',2,'10189',10164,0,2),(71,10154,'20',2,'10189',10165,0,1),(240,10154,'23',1,'10154',10164,0,2),(241,10154,'23',1,'10154',10165,0,2),(70,10154,'23',2,'10189',10165,0,1),(125,10154,'25',2,'10189',10164,0,2),(95,10154,'25',2,'10189',10165,0,1),(150,10154,'26',1,'10154',10164,0,2),(151,10154,'26',1,'10154',10165,0,2),(21,10154,'26',2,'10189',10165,0,1),(143,10154,'27',1,'10154',10164,0,2),(144,10154,'27',1,'10154',10165,0,2),(15,10154,'27',2,'10189',10165,0,1),(256,10154,'28',1,'10154',10164,0,4),(257,10154,'28',1,'10154',10165,0,4),(123,10154,'28',2,'10189',10164,0,2),(78,10154,'28',2,'10189',10165,0,1),(176,10154,'29',1,'10154',10164,0,2),(177,10154,'29',1,'10154',10165,0,2),(37,10154,'29',2,'10189',10165,0,1),(237,10154,'3',1,'10154',10162,0,2),(238,10154,'3',1,'10154',10164,0,2),(239,10154,'3',1,'10154',10165,0,2),(69,10154,'3',2,'10189',10165,0,1),(341,10154,'3',4,'10323_LAYOUT_3',10162,0,1),(339,10154,'3',4,'10323_LAYOUT_3',10163,0,15),(340,10154,'3',4,'10323_LAYOUT_3',10170,0,1),(203,10154,'30',1,'10154',10164,0,2),(204,10154,'30',1,'10154',10165,0,2),(49,10154,'30',2,'10189',10165,0,1),(280,10154,'31',1,'10154',10162,0,2),(281,10154,'31',1,'10154',10164,0,2),(282,10154,'31',1,'10154',10165,0,2),(91,10154,'31',2,'10189',10165,0,1),(289,10154,'33',1,'10154',10162,0,2),(290,10154,'33',1,'10154',10164,0,2),(291,10154,'33',1,'10154',10165,0,2),(97,10154,'33',2,'10189',10165,0,1),(322,10154,'33',4,'10313_LAYOUT_33',10162,0,1),(320,10154,'33',4,'10313_LAYOUT_33',10163,0,15),(321,10154,'33',4,'10313_LAYOUT_33',10170,0,1),(271,10154,'34',1,'10154',10164,0,2),(272,10154,'34',1,'10154',10165,0,2),(86,10154,'34',2,'10189',10165,0,1),(148,10154,'36',1,'10154',10164,0,2),(149,10154,'36',1,'10154',10165,0,2),(20,10154,'36',2,'10189',10165,0,1),(350,10154,'36',4,'10332_LAYOUT_36',10162,0,1),(348,10154,'36',4,'10332_LAYOUT_36',10163,0,15),(349,10154,'36',4,'10332_LAYOUT_36',10170,0,1),(191,10154,'39',1,'10154',10164,0,2),(192,10154,'39',1,'10154',10165,0,2),(44,10154,'39',2,'10189',10165,0,1),(258,10154,'47',1,'10154',10162,0,2),(259,10154,'47',1,'10154',10164,0,2),(260,10154,'47',1,'10154',10165,0,2),(80,10154,'47',2,'10189',10165,0,1),(205,10154,'48',1,'10154',10164,0,2),(206,10154,'48',1,'10154',10165,0,2),(51,10154,'48',2,'10189',10165,0,1),(283,10154,'50',1,'10154',10162,0,2),(284,10154,'50',1,'10154',10164,0,2),(285,10154,'50',1,'10154',10165,0,2),(93,10154,'50',2,'10189',10165,0,1),(269,10154,'54',1,'10154',10164,0,2),(270,10154,'54',1,'10154',10165,0,2),(85,10154,'54',2,'10189',10165,0,1),(231,10154,'56',1,'10154',10162,0,2),(232,10154,'56',1,'10154',10164,0,2),(233,10154,'56',1,'10154',10165,0,2),(66,10154,'56',2,'10189',10165,0,1),(183,10154,'58',1,'10154',10162,0,2),(184,10154,'58',1,'10154',10164,0,2),(185,10154,'58',1,'10154',10165,0,2),(41,10154,'58',2,'10189',10165,0,1),(299,10154,'59',1,'10154',10164,0,2),(300,10154,'59',1,'10154',10165,0,2),(103,10154,'59',2,'10189',10165,0,1),(275,10154,'61',1,'10154',10164,0,2),(276,10154,'61',1,'10154',10165,0,2),(89,10154,'61',2,'10189',10165,0,1),(207,10154,'62',1,'10154',10164,0,2),(208,10154,'62',1,'10154',10165,0,2),(55,10154,'62',2,'10189',10165,0,1),(157,10154,'64',1,'10154',10162,0,2),(158,10154,'64',1,'10154',10164,0,2),(159,10154,'64',1,'10154',10165,0,2),(25,10154,'64',2,'10189',10165,0,1),(138,10154,'66',1,'10154',10164,0,2),(139,10154,'66',1,'10154',10165,0,2),(13,10154,'66',2,'10189',10165,0,1),(295,10154,'67',1,'10154',10164,0,2),(296,10154,'67',1,'10154',10165,0,2),(101,10154,'67',2,'10189',10165,0,1),(250,10154,'70',1,'10154',10164,0,2),(251,10154,'70',1,'10154',10165,0,2),(75,10154,'70',2,'10189',10165,0,1),(186,10154,'71',1,'10154',10162,0,2),(187,10154,'71',1,'10154',10164,0,2),(188,10154,'71',1,'10154',10165,0,2),(42,10154,'71',2,'10189',10165,0,1),(277,10154,'73',1,'10154',10162,0,2),(278,10154,'73',1,'10154',10164,0,2),(279,10154,'73',1,'10154',10165,0,2),(90,10154,'73',2,'10189',10165,0,1),(223,10154,'77',1,'10154',10162,0,2),(224,10154,'77',1,'10154',10164,0,2),(225,10154,'77',1,'10154',10165,0,2),(63,10154,'77',2,'10189',10165,0,1),(181,10154,'8',1,'10154',10164,0,4),(182,10154,'8',1,'10154',10165,0,4),(116,10154,'8',2,'10189',10164,0,2),(40,10154,'8',2,'10189',10165,0,1),(266,10154,'82',1,'10154',10162,0,2),(267,10154,'82',1,'10154',10164,0,2),(268,10154,'82',1,'10154',10165,0,2),(83,10154,'82',2,'10189',10165,0,1),(245,10154,'83',1,'10154',10164,0,4),(246,10154,'83',1,'10154',10165,0,4),(72,10154,'83',2,'10189',10165,0,1),(212,10154,'84',1,'10154',10164,0,4),(213,10154,'84',1,'10154',10165,0,4),(59,10154,'84',2,'10189',10165,0,1),(195,10154,'85',1,'10154',10162,0,2),(196,10154,'85',1,'10154',10164,0,2),(197,10154,'85',1,'10154',10165,0,2),(46,10154,'85',2,'10189',10165,0,1),(255,10154,'9',1,'10154',10161,0,2),(77,10154,'9',2,'10189',10165,0,1),(11,10154,'90',1,'10154',10165,0,16384),(189,10154,'97',1,'10154',10164,0,2),(190,10154,'97',1,'10154',10165,0,2),(43,10154,'97',2,'10189',10165,0,1),(136,10154,'98',1,'10154',10164,0,4),(137,10154,'98',1,'10154',10165,0,4),(110,10154,'98',2,'10189',10164,0,2),(12,10154,'98',2,'10189',10165,0,1),(122,10154,'99',2,'10189',10164,0,2),(74,10154,'99',2,'10189',10165,0,1),(126,10154,'com.liferay.portal.model.Group',2,'10189',10164,0,4096),(107,10154,'com.liferay.portal.model.Layout',2,'10189',10165,0,1),(7,10154,'com.liferay.portal.model.Layout',4,'10175',10162,0,1),(5,10154,'com.liferay.portal.model.Layout',4,'10175',10163,10158,1023),(6,10154,'com.liferay.portal.model.Layout',4,'10175',10170,0,19),(10,10154,'com.liferay.portal.model.Layout',4,'10183',10162,0,1),(8,10154,'com.liferay.portal.model.Layout',4,'10183',10163,10158,1023),(9,10154,'com.liferay.portal.model.Layout',4,'10183',10170,0,19),(317,10154,'com.liferay.portal.model.Layout',4,'10313',10163,10158,1023),(318,10154,'com.liferay.portal.model.Layout',4,'10313',10170,0,19),(331,10154,'com.liferay.portal.model.Layout',4,'10323',10163,10158,1023),(332,10154,'com.liferay.portal.model.Layout',4,'10323',10170,0,19),(346,10154,'com.liferay.portal.model.Layout',4,'10332',10163,10158,1023),(347,10154,'com.liferay.portal.model.Layout',4,'10332',10170,0,19),(364,10154,'com.liferay.portal.model.Layout',4,'10346',10162,0,1),(362,10154,'com.liferay.portal.model.Layout',4,'10346',10163,10158,1023),(363,10154,'com.liferay.portal.model.Layout',4,'10346',10170,0,19),(367,10154,'com.liferay.portal.model.Layout',4,'10352',10162,0,1),(365,10154,'com.liferay.portal.model.Layout',4,'10352',10163,10158,1023),(366,10154,'com.liferay.portal.model.Layout',4,'10352',10170,0,19),(370,10154,'com.liferay.portal.model.Layout',4,'10358',10162,0,1),(368,10154,'com.liferay.portal.model.Layout',4,'10358',10163,10158,1023),(369,10154,'com.liferay.portal.model.Layout',4,'10358',10170,0,19),(377,10154,'com.liferay.portal.model.Layout',4,'10372',10162,0,1),(375,10154,'com.liferay.portal.model.Layout',4,'10372',10163,10158,1023),(376,10154,'com.liferay.portal.model.Layout',4,'10372',10170,0,19),(380,10154,'com.liferay.portal.model.Layout',4,'10380',10162,0,1),(378,10154,'com.liferay.portal.model.Layout',4,'10380',10163,10158,1023),(379,10154,'com.liferay.portal.model.Layout',4,'10380',10170,0,19),(383,10154,'com.liferay.portal.model.Layout',4,'10386',10162,0,1),(381,10154,'com.liferay.portal.model.Layout',4,'10386',10163,10158,1023),(382,10154,'com.liferay.portal.model.Layout',4,'10386',10170,0,19),(386,10154,'com.liferay.portal.model.Layout',4,'10392',10162,0,1),(384,10154,'com.liferay.portal.model.Layout',4,'10392',10163,10158,1023),(385,10154,'com.liferay.portal.model.Layout',4,'10392',10170,0,19),(316,10154,'com.liferay.portal.model.LayoutPrototype',4,'10309',10163,10158,15),(330,10154,'com.liferay.portal.model.LayoutPrototype',4,'10319',10163,10158,15),(345,10154,'com.liferay.portal.model.LayoutPrototype',4,'10328',10163,10158,15),(358,10154,'com.liferay.portal.model.LayoutSetPrototype',4,'10337',10163,10158,15),(371,10154,'com.liferay.portal.model.LayoutSetPrototype',4,'10363',10163,10158,15),(135,10154,'com.liferay.portal.model.User',4,'10196',10163,10196,31),(127,10154,'com.liferay.portlet.asset',2,'10189',10164,0,30),(319,10154,'com.liferay.portlet.asset.model.AssetVocabulary',4,'10316',10163,10158,15),(128,10154,'com.liferay.portlet.blogs',2,'10189',10164,0,14),(108,10154,'com.liferay.portlet.blogs',2,'10189',10165,0,14),(323,10154,'com.liferay.portlet.blogs',4,'10310',10163,0,14),(129,10154,'com.liferay.portlet.bookmarks',2,'10189',10164,0,15),(130,10154,'com.liferay.portlet.calendar',2,'10189',10164,0,14),(109,10154,'com.liferay.portlet.calendar',2,'10189',10165,0,14),(131,10154,'com.liferay.portlet.documentlibrary',2,'10189',10164,0,511),(308,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntryType',4,'10300',10163,10158,15),(310,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntryType',4,'10302',10163,10158,15),(312,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntryType',4,'10304',10163,10158,15),(314,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntryType',4,'10306',10163,10158,15),(304,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10297',10163,10158,15),(305,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10298',10163,10158,15),(306,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10299',10163,10158,15),(307,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10301',10163,10158,15),(309,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10303',10163,10158,15),(311,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10305',10163,10158,15),(313,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10307',10163,10158,15),(315,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10308',10163,10158,15),(387,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10399',10163,10158,15),(388,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10400',10163,10158,15),(389,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10401',10163,10158,15),(390,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10402',10163,10158,15),(391,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10403',10163,10158,15),(392,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10404',10163,10158,15),(132,10154,'com.liferay.portlet.messageboards',2,'10189',10164,0,2047),(133,10154,'com.liferay.portlet.polls',2,'10189',10164,0,6),(134,10154,'com.liferay.portlet.wiki',2,'10189',10164,0,6),(351,10154,'com.liferay.portlet.wiki',4,'10329',10163,0,6);
/*!40000 ALTER TABLE `ResourcePermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ResourceTypePermission`
--

DROP TABLE IF EXISTS `ResourceTypePermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResourceTypePermission` (
  `resourceTypePermissionId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `roleId` bigint(20) DEFAULT NULL,
  `actionIds` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`resourceTypePermissionId`),
  UNIQUE KEY `IX_BA497163` (`companyId`,`groupId`,`name`,`roleId`),
  KEY `IX_7D81F66F` (`companyId`,`name`,`roleId`),
  KEY `IX_A82690E2` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ResourceTypePermission`
--

LOCK TABLES `ResourceTypePermission` WRITE;
/*!40000 ALTER TABLE `ResourceTypePermission` DISABLE KEYS */;
/*!40000 ALTER TABLE `ResourceTypePermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Resource_`
--

DROP TABLE IF EXISTS `Resource_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Resource_` (
  `resourceId` bigint(20) NOT NULL,
  `codeId` bigint(20) DEFAULT NULL,
  `primKey` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`resourceId`),
  UNIQUE KEY `IX_67DE7856` (`codeId`,`primKey`),
  KEY `IX_2578FBD3` (`codeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Resource_`
--

LOCK TABLES `Resource_` WRITE;
/*!40000 ALTER TABLE `Resource_` DISABLE KEYS */;
/*!40000 ALTER TABLE `Resource_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Role_`
--

DROP TABLE IF EXISTS `Role_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Role_` (
  `roleId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `title` longtext,
  `description` longtext,
  `type_` int(11) DEFAULT NULL,
  `subtype` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`roleId`),
  UNIQUE KEY `IX_A88E424E` (`companyId`,`classNameId`,`classPK`),
  UNIQUE KEY `IX_EBC931B8` (`companyId`,`name`),
  KEY `IX_449A10B9` (`companyId`),
  KEY `IX_F436EC8E` (`name`),
  KEY `IX_5EB4E2FB` (`subtype`),
  KEY `IX_CBE204` (`type_`,`subtype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Role_`
--

LOCK TABLES `Role_` WRITE;
/*!40000 ALTER TABLE `Role_` DISABLE KEYS */;
INSERT INTO `Role_` VALUES (10161,10154,10004,10161,'Administrator','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Administrators are super users who can do anything.</Description></root>',1,''),(10162,10154,10004,10162,'Guest','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Unauthenticated users always have this role.</Description></root>',1,''),(10163,10154,10004,10163,'Owner','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">This is an implied role with respect to the objects users create.</Description></root>',1,''),(10164,10154,10004,10164,'Power User','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Power Users have their own personal site.</Description></root>',1,''),(10165,10154,10004,10165,'User','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Authenticated users should be assigned this role.</Description></root>',1,''),(10166,10154,10004,10166,'Organization Administrator','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Organization Administrators are super users of their organization but cannot make other users into Organization Administrators.</Description></root>',3,''),(10167,10154,10004,10167,'Organization Owner','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Organization Owners are super users of their organization and can assign organization roles to users.</Description></root>',3,''),(10168,10154,10004,10168,'Organization User','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">All users who belong to an organization have this role within that organization.</Description></root>',3,''),(10169,10154,10004,10169,'Site Administrator','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Site Administrators are super users of their site but cannot make other users into Site Administrators.</Description></root>',2,''),(10170,10154,10004,10170,'Site Member','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">All users who belong to a site have this role within that site.</Description></root>',2,''),(10171,10154,10004,10171,'Site Owner','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Site Owners are super users of their site and can assign site roles to users.</Description></root>',2,'');
/*!40000 ALTER TABLE `Role_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Roles_Permissions`
--

DROP TABLE IF EXISTS `Roles_Permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Roles_Permissions` (
  `roleId` bigint(20) NOT NULL,
  `permissionId` bigint(20) NOT NULL,
  PRIMARY KEY (`roleId`,`permissionId`),
  KEY `IX_7A3619C6` (`permissionId`),
  KEY `IX_E04E486D` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Roles_Permissions`
--

LOCK TABLES `Roles_Permissions` WRITE;
/*!40000 ALTER TABLE `Roles_Permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `Roles_Permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SCFrameworkVersi_SCProductVers`
--

DROP TABLE IF EXISTS `SCFrameworkVersi_SCProductVers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SCFrameworkVersi_SCProductVers` (
  `frameworkVersionId` bigint(20) NOT NULL,
  `productVersionId` bigint(20) NOT NULL,
  PRIMARY KEY (`frameworkVersionId`,`productVersionId`),
  KEY `IX_3BB93ECA` (`frameworkVersionId`),
  KEY `IX_E8D33FF9` (`productVersionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SCFrameworkVersi_SCProductVers`
--

LOCK TABLES `SCFrameworkVersi_SCProductVers` WRITE;
/*!40000 ALTER TABLE `SCFrameworkVersi_SCProductVers` DISABLE KEYS */;
/*!40000 ALTER TABLE `SCFrameworkVersi_SCProductVers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SCFrameworkVersion`
--

DROP TABLE IF EXISTS `SCFrameworkVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SCFrameworkVersion` (
  `frameworkVersionId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `url` longtext,
  `active_` tinyint(4) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`frameworkVersionId`),
  KEY `IX_C98C0D78` (`companyId`),
  KEY `IX_272991FA` (`groupId`),
  KEY `IX_6E1764F` (`groupId`,`active_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SCFrameworkVersion`
--

LOCK TABLES `SCFrameworkVersion` WRITE;
/*!40000 ALTER TABLE `SCFrameworkVersion` DISABLE KEYS */;
/*!40000 ALTER TABLE `SCFrameworkVersion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SCLicense`
--

DROP TABLE IF EXISTS `SCLicense`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SCLicense` (
  `licenseId` bigint(20) NOT NULL,
  `name` varchar(75) DEFAULT NULL,
  `url` longtext,
  `openSource` tinyint(4) DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  `recommended` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`licenseId`),
  KEY `IX_1C841592` (`active_`),
  KEY `IX_5327BB79` (`active_`,`recommended`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SCLicense`
--

LOCK TABLES `SCLicense` WRITE;
/*!40000 ALTER TABLE `SCLicense` DISABLE KEYS */;
/*!40000 ALTER TABLE `SCLicense` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SCLicenses_SCProductEntries`
--

DROP TABLE IF EXISTS `SCLicenses_SCProductEntries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SCLicenses_SCProductEntries` (
  `licenseId` bigint(20) NOT NULL,
  `productEntryId` bigint(20) NOT NULL,
  PRIMARY KEY (`licenseId`,`productEntryId`),
  KEY `IX_27006638` (`licenseId`),
  KEY `IX_D7710A66` (`productEntryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SCLicenses_SCProductEntries`
--

LOCK TABLES `SCLicenses_SCProductEntries` WRITE;
/*!40000 ALTER TABLE `SCLicenses_SCProductEntries` DISABLE KEYS */;
/*!40000 ALTER TABLE `SCLicenses_SCProductEntries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SCProductEntry`
--

DROP TABLE IF EXISTS `SCProductEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SCProductEntry` (
  `productEntryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `shortDescription` longtext,
  `longDescription` longtext,
  `pageURL` longtext,
  `author` varchar(75) DEFAULT NULL,
  `repoGroupId` varchar(75) DEFAULT NULL,
  `repoArtifactId` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`productEntryId`),
  KEY `IX_5D25244F` (`companyId`),
  KEY `IX_72F87291` (`groupId`),
  KEY `IX_98E6A9CB` (`groupId`,`userId`),
  KEY `IX_7311E812` (`repoGroupId`,`repoArtifactId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SCProductEntry`
--

LOCK TABLES `SCProductEntry` WRITE;
/*!40000 ALTER TABLE `SCProductEntry` DISABLE KEYS */;
/*!40000 ALTER TABLE `SCProductEntry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SCProductScreenshot`
--

DROP TABLE IF EXISTS `SCProductScreenshot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SCProductScreenshot` (
  `productScreenshotId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `productEntryId` bigint(20) DEFAULT NULL,
  `thumbnailId` bigint(20) DEFAULT NULL,
  `fullImageId` bigint(20) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`productScreenshotId`),
  KEY `IX_AE8224CC` (`fullImageId`),
  KEY `IX_467956FD` (`productEntryId`),
  KEY `IX_DA913A55` (`productEntryId`,`priority`),
  KEY `IX_6C572DAC` (`thumbnailId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SCProductScreenshot`
--

LOCK TABLES `SCProductScreenshot` WRITE;
/*!40000 ALTER TABLE `SCProductScreenshot` DISABLE KEYS */;
/*!40000 ALTER TABLE `SCProductScreenshot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SCProductVersion`
--

DROP TABLE IF EXISTS `SCProductVersion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SCProductVersion` (
  `productVersionId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `productEntryId` bigint(20) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  `changeLog` longtext,
  `downloadPageURL` longtext,
  `directDownloadURL` varchar(2000) DEFAULT NULL,
  `repoStoreArtifact` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`productVersionId`),
  KEY `IX_7020130F` (`directDownloadURL`(255)),
  KEY `IX_8377A211` (`productEntryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SCProductVersion`
--

LOCK TABLES `SCProductVersion` WRITE;
/*!40000 ALTER TABLE `SCProductVersion` DISABLE KEYS */;
/*!40000 ALTER TABLE `SCProductVersion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServiceComponent`
--

DROP TABLE IF EXISTS `ServiceComponent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ServiceComponent` (
  `serviceComponentId` bigint(20) NOT NULL,
  `buildNamespace` varchar(75) DEFAULT NULL,
  `buildNumber` bigint(20) DEFAULT NULL,
  `buildDate` bigint(20) DEFAULT NULL,
  `data_` longtext,
  PRIMARY KEY (`serviceComponentId`),
  UNIQUE KEY `IX_4F0315B8` (`buildNamespace`,`buildNumber`),
  KEY `IX_7338606F` (`buildNamespace`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServiceComponent`
--

LOCK TABLES `ServiceComponent` WRITE;
/*!40000 ALTER TABLE `ServiceComponent` DISABLE KEYS */;
/*!40000 ALTER TABLE `ServiceComponent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Shard`
--

DROP TABLE IF EXISTS `Shard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Shard` (
  `shardId` bigint(20) NOT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`shardId`),
  KEY `IX_DA5F4359` (`classNameId`,`classPK`),
  KEY `IX_941BA8C3` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Shard`
--

LOCK TABLES `Shard` WRITE;
/*!40000 ALTER TABLE `Shard` DISABLE KEYS */;
INSERT INTO `Shard` VALUES (10155,10021,10154,'default');
/*!40000 ALTER TABLE `Shard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShoppingCart`
--

DROP TABLE IF EXISTS `ShoppingCart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ShoppingCart` (
  `cartId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `itemIds` longtext,
  `couponCodes` varchar(75) DEFAULT NULL,
  `altShipping` int(11) DEFAULT NULL,
  `insure` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`cartId`),
  UNIQUE KEY `IX_FC46FE16` (`groupId`,`userId`),
  KEY `IX_C28B41DC` (`groupId`),
  KEY `IX_54101CC8` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShoppingCart`
--

LOCK TABLES `ShoppingCart` WRITE;
/*!40000 ALTER TABLE `ShoppingCart` DISABLE KEYS */;
/*!40000 ALTER TABLE `ShoppingCart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShoppingCategory`
--

DROP TABLE IF EXISTS `ShoppingCategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ShoppingCategory` (
  `categoryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `parentCategoryId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`categoryId`),
  KEY `IX_5F615D3E` (`groupId`),
  KEY `IX_1E6464F5` (`groupId`,`parentCategoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShoppingCategory`
--

LOCK TABLES `ShoppingCategory` WRITE;
/*!40000 ALTER TABLE `ShoppingCategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `ShoppingCategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShoppingCoupon`
--

DROP TABLE IF EXISTS `ShoppingCoupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ShoppingCoupon` (
  `couponId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `code_` varchar(75) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `startDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  `limitCategories` longtext,
  `limitSkus` longtext,
  `minOrder` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `discountType` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`couponId`),
  UNIQUE KEY `IX_DC60CFAE` (`code_`),
  KEY `IX_3251AF16` (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShoppingCoupon`
--

LOCK TABLES `ShoppingCoupon` WRITE;
/*!40000 ALTER TABLE `ShoppingCoupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `ShoppingCoupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShoppingItem`
--

DROP TABLE IF EXISTS `ShoppingItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ShoppingItem` (
  `itemId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `categoryId` bigint(20) DEFAULT NULL,
  `sku` varchar(75) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `description` longtext,
  `properties` longtext,
  `fields_` tinyint(4) DEFAULT NULL,
  `fieldsQuantities` longtext,
  `minQuantity` int(11) DEFAULT NULL,
  `maxQuantity` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `taxable` tinyint(4) DEFAULT NULL,
  `shipping` double DEFAULT NULL,
  `useShippingFormula` tinyint(4) DEFAULT NULL,
  `requiresShipping` tinyint(4) DEFAULT NULL,
  `stockQuantity` int(11) DEFAULT NULL,
  `featured_` tinyint(4) DEFAULT NULL,
  `sale_` tinyint(4) DEFAULT NULL,
  `smallImage` tinyint(4) DEFAULT NULL,
  `smallImageId` bigint(20) DEFAULT NULL,
  `smallImageURL` longtext,
  `mediumImage` tinyint(4) DEFAULT NULL,
  `mediumImageId` bigint(20) DEFAULT NULL,
  `mediumImageURL` longtext,
  `largeImage` tinyint(4) DEFAULT NULL,
  `largeImageId` bigint(20) DEFAULT NULL,
  `largeImageURL` longtext,
  PRIMARY KEY (`itemId`),
  UNIQUE KEY `IX_1C717CA6` (`companyId`,`sku`),
  KEY `IX_FEFE7D76` (`groupId`,`categoryId`),
  KEY `IX_903DC750` (`largeImageId`),
  KEY `IX_D217AB30` (`mediumImageId`),
  KEY `IX_FF203304` (`smallImageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShoppingItem`
--

LOCK TABLES `ShoppingItem` WRITE;
/*!40000 ALTER TABLE `ShoppingItem` DISABLE KEYS */;
/*!40000 ALTER TABLE `ShoppingItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShoppingItemField`
--

DROP TABLE IF EXISTS `ShoppingItemField`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ShoppingItemField` (
  `itemFieldId` bigint(20) NOT NULL,
  `itemId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `values_` longtext,
  `description` longtext,
  PRIMARY KEY (`itemFieldId`),
  KEY `IX_6D5F9B87` (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShoppingItemField`
--

LOCK TABLES `ShoppingItemField` WRITE;
/*!40000 ALTER TABLE `ShoppingItemField` DISABLE KEYS */;
/*!40000 ALTER TABLE `ShoppingItemField` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShoppingItemPrice`
--

DROP TABLE IF EXISTS `ShoppingItemPrice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ShoppingItemPrice` (
  `itemPriceId` bigint(20) NOT NULL,
  `itemId` bigint(20) DEFAULT NULL,
  `minQuantity` int(11) DEFAULT NULL,
  `maxQuantity` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `taxable` tinyint(4) DEFAULT NULL,
  `shipping` double DEFAULT NULL,
  `useShippingFormula` tinyint(4) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`itemPriceId`),
  KEY `IX_EA6FD516` (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShoppingItemPrice`
--

LOCK TABLES `ShoppingItemPrice` WRITE;
/*!40000 ALTER TABLE `ShoppingItemPrice` DISABLE KEYS */;
/*!40000 ALTER TABLE `ShoppingItemPrice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShoppingOrder`
--

DROP TABLE IF EXISTS `ShoppingOrder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ShoppingOrder` (
  `orderId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `number_` varchar(75) DEFAULT NULL,
  `tax` double DEFAULT NULL,
  `shipping` double DEFAULT NULL,
  `altShipping` varchar(75) DEFAULT NULL,
  `requiresShipping` tinyint(4) DEFAULT NULL,
  `insure` tinyint(4) DEFAULT NULL,
  `insurance` double DEFAULT NULL,
  `couponCodes` varchar(75) DEFAULT NULL,
  `couponDiscount` double DEFAULT NULL,
  `billingFirstName` varchar(75) DEFAULT NULL,
  `billingLastName` varchar(75) DEFAULT NULL,
  `billingEmailAddress` varchar(75) DEFAULT NULL,
  `billingCompany` varchar(75) DEFAULT NULL,
  `billingStreet` varchar(75) DEFAULT NULL,
  `billingCity` varchar(75) DEFAULT NULL,
  `billingState` varchar(75) DEFAULT NULL,
  `billingZip` varchar(75) DEFAULT NULL,
  `billingCountry` varchar(75) DEFAULT NULL,
  `billingPhone` varchar(75) DEFAULT NULL,
  `shipToBilling` tinyint(4) DEFAULT NULL,
  `shippingFirstName` varchar(75) DEFAULT NULL,
  `shippingLastName` varchar(75) DEFAULT NULL,
  `shippingEmailAddress` varchar(75) DEFAULT NULL,
  `shippingCompany` varchar(75) DEFAULT NULL,
  `shippingStreet` varchar(75) DEFAULT NULL,
  `shippingCity` varchar(75) DEFAULT NULL,
  `shippingState` varchar(75) DEFAULT NULL,
  `shippingZip` varchar(75) DEFAULT NULL,
  `shippingCountry` varchar(75) DEFAULT NULL,
  `shippingPhone` varchar(75) DEFAULT NULL,
  `ccName` varchar(75) DEFAULT NULL,
  `ccType` varchar(75) DEFAULT NULL,
  `ccNumber` varchar(75) DEFAULT NULL,
  `ccExpMonth` int(11) DEFAULT NULL,
  `ccExpYear` int(11) DEFAULT NULL,
  `ccVerNumber` varchar(75) DEFAULT NULL,
  `comments` longtext,
  `ppTxnId` varchar(75) DEFAULT NULL,
  `ppPaymentStatus` varchar(75) DEFAULT NULL,
  `ppPaymentGross` double DEFAULT NULL,
  `ppReceiverEmail` varchar(75) DEFAULT NULL,
  `ppPayerEmail` varchar(75) DEFAULT NULL,
  `sendOrderEmail` tinyint(4) DEFAULT NULL,
  `sendShippingEmail` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`orderId`),
  UNIQUE KEY `IX_D7D6E87A` (`number_`),
  KEY `IX_1D15553E` (`groupId`),
  KEY `IX_119B5630` (`groupId`,`userId`,`ppPaymentStatus`),
  KEY `IX_F474FD89` (`ppTxnId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShoppingOrder`
--

LOCK TABLES `ShoppingOrder` WRITE;
/*!40000 ALTER TABLE `ShoppingOrder` DISABLE KEYS */;
/*!40000 ALTER TABLE `ShoppingOrder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShoppingOrderItem`
--

DROP TABLE IF EXISTS `ShoppingOrderItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ShoppingOrderItem` (
  `orderItemId` bigint(20) NOT NULL,
  `orderId` bigint(20) DEFAULT NULL,
  `itemId` varchar(75) DEFAULT NULL,
  `sku` varchar(75) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `description` longtext,
  `properties` longtext,
  `price` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `shippedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`orderItemId`),
  KEY `IX_B5F82C7A` (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShoppingOrderItem`
--

LOCK TABLES `ShoppingOrderItem` WRITE;
/*!40000 ALTER TABLE `ShoppingOrderItem` DISABLE KEYS */;
/*!40000 ALTER TABLE `ShoppingOrderItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SocialActivity`
--

DROP TABLE IF EXISTS `SocialActivity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SocialActivity` (
  `activityId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createDate` bigint(20) DEFAULT NULL,
  `mirrorActivityId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  `extraData` longtext,
  `receiverUserId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`activityId`),
  UNIQUE KEY `IX_8F32DEC9` (`groupId`,`userId`,`createDate`,`classNameId`,`classPK`,`type_`,`receiverUserId`),
  KEY `IX_82E39A0C` (`classNameId`),
  KEY `IX_A853C757` (`classNameId`,`classPK`),
  KEY `IX_64B1BC66` (`companyId`),
  KEY `IX_2A2468` (`groupId`),
  KEY `IX_FB604DC7` (`groupId`,`userId`,`classNameId`,`classPK`,`type_`,`receiverUserId`),
  KEY `IX_1271F25F` (`mirrorActivityId`),
  KEY `IX_1F00C374` (`mirrorActivityId`,`classNameId`,`classPK`),
  KEY `IX_121CA3CB` (`receiverUserId`),
  KEY `IX_3504B8BC` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SocialActivity`
--

LOCK TABLES `SocialActivity` WRITE;
/*!40000 ALTER TABLE `SocialActivity` DISABLE KEYS */;
/*!40000 ALTER TABLE `SocialActivity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SocialActivityAchievement`
--

DROP TABLE IF EXISTS `SocialActivityAchievement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SocialActivityAchievement` (
  `activityAchievementId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createDate` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `firstInGroup` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`activityAchievementId`),
  UNIQUE KEY `IX_D4390CAA` (`groupId`,`userId`,`name`),
  KEY `IX_E14B1F1` (`groupId`),
  KEY `IX_83E16F2F` (`groupId`,`firstInGroup`),
  KEY `IX_8F6408F0` (`groupId`,`name`),
  KEY `IX_C8FD892B` (`groupId`,`userId`),
  KEY `IX_AABC18E9` (`groupId`,`userId`,`firstInGroup`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SocialActivityAchievement`
--

LOCK TABLES `SocialActivityAchievement` WRITE;
/*!40000 ALTER TABLE `SocialActivityAchievement` DISABLE KEYS */;
/*!40000 ALTER TABLE `SocialActivityAchievement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SocialActivityCounter`
--

DROP TABLE IF EXISTS `SocialActivityCounter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SocialActivityCounter` (
  `activityCounterId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `ownerType` int(11) DEFAULT NULL,
  `currentValue` int(11) DEFAULT NULL,
  `totalValue` int(11) DEFAULT NULL,
  `graceValue` int(11) DEFAULT NULL,
  `startPeriod` int(11) DEFAULT NULL,
  `endPeriod` int(11) DEFAULT NULL,
  PRIMARY KEY (`activityCounterId`),
  UNIQUE KEY `IX_1B7E3B67` (`groupId`,`classNameId`,`classPK`,`name`,`ownerType`,`endPeriod`),
  UNIQUE KEY `IX_374B35AE` (`groupId`,`classNameId`,`classPK`,`name`,`ownerType`,`startPeriod`),
  KEY `IX_A4B9A23B` (`classNameId`,`classPK`),
  KEY `IX_926CDD04` (`groupId`,`classNameId`,`classPK`,`ownerType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SocialActivityCounter`
--

LOCK TABLES `SocialActivityCounter` WRITE;
/*!40000 ALTER TABLE `SocialActivityCounter` DISABLE KEYS */;
/*!40000 ALTER TABLE `SocialActivityCounter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SocialActivityLimit`
--

DROP TABLE IF EXISTS `SocialActivityLimit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SocialActivityLimit` (
  `activityLimitId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `activityType` int(11) DEFAULT NULL,
  `activityCounterName` varchar(75) DEFAULT NULL,
  `value` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`activityLimitId`),
  UNIQUE KEY `IX_F1C1A617` (`groupId`,`userId`,`classNameId`,`classPK`,`activityType`,`activityCounterName`),
  KEY `IX_B15863FA` (`classNameId`,`classPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SocialActivityLimit`
--

LOCK TABLES `SocialActivityLimit` WRITE;
/*!40000 ALTER TABLE `SocialActivityLimit` DISABLE KEYS */;
/*!40000 ALTER TABLE `SocialActivityLimit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SocialActivitySetting`
--

DROP TABLE IF EXISTS `SocialActivitySetting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SocialActivitySetting` (
  `activitySettingId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `activityType` int(11) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `value` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`activitySettingId`),
  KEY `IX_384788CD` (`groupId`,`activityType`),
  KEY `IX_1E9CF33B` (`groupId`,`classNameId`,`activityType`),
  KEY `IX_D984AABA` (`groupId`,`classNameId`,`activityType`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SocialActivitySetting`
--

LOCK TABLES `SocialActivitySetting` WRITE;
/*!40000 ALTER TABLE `SocialActivitySetting` DISABLE KEYS */;
/*!40000 ALTER TABLE `SocialActivitySetting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SocialRelation`
--

DROP TABLE IF EXISTS `SocialRelation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SocialRelation` (
  `uuid_` varchar(75) DEFAULT NULL,
  `relationId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` bigint(20) DEFAULT NULL,
  `userId1` bigint(20) DEFAULT NULL,
  `userId2` bigint(20) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  PRIMARY KEY (`relationId`),
  UNIQUE KEY `IX_12A92145` (`userId1`,`userId2`,`type_`),
  KEY `IX_61171E99` (`companyId`),
  KEY `IX_95135D1C` (`companyId`,`type_`),
  KEY `IX_C31A64C6` (`type_`),
  KEY `IX_5A40CDCC` (`userId1`),
  KEY `IX_4B52BE89` (`userId1`,`type_`),
  KEY `IX_B5C9C690` (`userId1`,`userId2`),
  KEY `IX_5A40D18D` (`userId2`),
  KEY `IX_3F9C2FA8` (`userId2`,`type_`),
  KEY `IX_F0CA24A5` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SocialRelation`
--

LOCK TABLES `SocialRelation` WRITE;
/*!40000 ALTER TABLE `SocialRelation` DISABLE KEYS */;
/*!40000 ALTER TABLE `SocialRelation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SocialRequest`
--

DROP TABLE IF EXISTS `SocialRequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SocialRequest` (
  `uuid_` varchar(75) DEFAULT NULL,
  `requestId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createDate` bigint(20) DEFAULT NULL,
  `modifiedDate` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  `extraData` longtext,
  `receiverUserId` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`requestId`),
  UNIQUE KEY `IX_36A90CA7` (`userId`,`classNameId`,`classPK`,`type_`,`receiverUserId`),
  UNIQUE KEY `IX_4F973EFE` (`uuid_`,`groupId`),
  KEY `IX_D3425487` (`classNameId`,`classPK`,`type_`,`receiverUserId`,`status`),
  KEY `IX_A90FE5A0` (`companyId`),
  KEY `IX_32292ED1` (`receiverUserId`),
  KEY `IX_D9380CB7` (`receiverUserId`,`status`),
  KEY `IX_80F7A9C2` (`userId`),
  KEY `IX_CC86A444` (`userId`,`classNameId`,`classPK`,`type_`,`status`),
  KEY `IX_AB5906A8` (`userId`,`status`),
  KEY `IX_49D5872C` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SocialRequest`
--

LOCK TABLES `SocialRequest` WRITE;
/*!40000 ALTER TABLE `SocialRequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `SocialRequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Subscription`
--

DROP TABLE IF EXISTS `Subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Subscription` (
  `subscriptionId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `frequency` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`subscriptionId`),
  UNIQUE KEY `IX_2E1A92D4` (`companyId`,`userId`,`classNameId`,`classPK`),
  KEY `IX_786D171A` (`companyId`,`classNameId`,`classPK`),
  KEY `IX_54243AFD` (`userId`),
  KEY `IX_E8F34171` (`userId`,`classNameId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subscription`
--

LOCK TABLES `Subscription` WRITE;
/*!40000 ALTER TABLE `Subscription` DISABLE KEYS */;
/*!40000 ALTER TABLE `Subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Team`
--

DROP TABLE IF EXISTS `Team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Team` (
  `teamId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`teamId`),
  UNIQUE KEY `IX_143DC786` (`groupId`,`name`),
  KEY `IX_AE6E9907` (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Team`
--

LOCK TABLES `Team` WRITE;
/*!40000 ALTER TABLE `Team` DISABLE KEYS */;
/*!40000 ALTER TABLE `Team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ticket`
--

DROP TABLE IF EXISTS `Ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Ticket` (
  `ticketId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `key_` varchar(75) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  `extraInfo` longtext,
  `expirationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`ticketId`),
  KEY `IX_B2468446` (`key_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ticket`
--

LOCK TABLES `Ticket` WRITE;
/*!40000 ALTER TABLE `Ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `Ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserGroup`
--

DROP TABLE IF EXISTS `UserGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserGroup` (
  `userGroupId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `parentUserGroupId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `addedByLDAPImport` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`userGroupId`),
  UNIQUE KEY `IX_23EAD0D` (`companyId`,`name`),
  KEY `IX_524FEFCE` (`companyId`),
  KEY `IX_69771487` (`companyId`,`parentUserGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserGroup`
--

LOCK TABLES `UserGroup` WRITE;
/*!40000 ALTER TABLE `UserGroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserGroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserGroupGroupRole`
--

DROP TABLE IF EXISTS `UserGroupGroupRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserGroupGroupRole` (
  `userGroupId` bigint(20) NOT NULL,
  `groupId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`userGroupId`,`groupId`,`roleId`),
  KEY `IX_CCBE4063` (`groupId`),
  KEY `IX_CAB0CCC8` (`groupId`,`roleId`),
  KEY `IX_1CDF88C` (`roleId`),
  KEY `IX_DCDED558` (`userGroupId`),
  KEY `IX_73C52252` (`userGroupId`,`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserGroupGroupRole`
--

LOCK TABLES `UserGroupGroupRole` WRITE;
/*!40000 ALTER TABLE `UserGroupGroupRole` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserGroupGroupRole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserGroupRole`
--

DROP TABLE IF EXISTS `UserGroupRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserGroupRole` (
  `userId` bigint(20) NOT NULL,
  `groupId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`groupId`,`roleId`),
  KEY `IX_1B988D7A` (`groupId`),
  KEY `IX_871412DF` (`groupId`,`roleId`),
  KEY `IX_887A2C95` (`roleId`),
  KEY `IX_887BE56A` (`userId`),
  KEY `IX_4D040680` (`userId`,`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserGroupRole`
--

LOCK TABLES `UserGroupRole` WRITE;
/*!40000 ALTER TABLE `UserGroupRole` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserGroupRole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserGroups_Teams`
--

DROP TABLE IF EXISTS `UserGroups_Teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserGroups_Teams` (
  `userGroupId` bigint(20) NOT NULL,
  `teamId` bigint(20) NOT NULL,
  PRIMARY KEY (`userGroupId`,`teamId`),
  KEY `IX_31FB0B08` (`teamId`),
  KEY `IX_7F187E63` (`userGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserGroups_Teams`
--

LOCK TABLES `UserGroups_Teams` WRITE;
/*!40000 ALTER TABLE `UserGroups_Teams` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserGroups_Teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserIdMapper`
--

DROP TABLE IF EXISTS `UserIdMapper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserIdMapper` (
  `userIdMapperId` bigint(20) NOT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `description` varchar(75) DEFAULT NULL,
  `externalUserId` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`userIdMapperId`),
  UNIQUE KEY `IX_41A32E0D` (`type_`,`externalUserId`),
  UNIQUE KEY `IX_D1C44A6E` (`userId`,`type_`),
  KEY `IX_E60EA987` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserIdMapper`
--

LOCK TABLES `UserIdMapper` WRITE;
/*!40000 ALTER TABLE `UserIdMapper` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserIdMapper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserNotificationEvent`
--

DROP TABLE IF EXISTS `UserNotificationEvent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserNotificationEvent` (
  `uuid_` varchar(75) DEFAULT NULL,
  `userNotificationEventId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `deliverBy` bigint(20) DEFAULT NULL,
  `payload` longtext,
  `archived` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`userNotificationEventId`),
  KEY `IX_3E5D78C4` (`userId`),
  KEY `IX_3DBB361A` (`userId`,`archived`),
  KEY `IX_ECD8CFEA` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserNotificationEvent`
--

LOCK TABLES `UserNotificationEvent` WRITE;
/*!40000 ALTER TABLE `UserNotificationEvent` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserNotificationEvent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserTracker`
--

DROP TABLE IF EXISTS `UserTracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserTracker` (
  `userTrackerId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `sessionId` varchar(200) DEFAULT NULL,
  `remoteAddr` varchar(75) DEFAULT NULL,
  `remoteHost` varchar(75) DEFAULT NULL,
  `userAgent` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`userTrackerId`),
  KEY `IX_29BA1CF5` (`companyId`),
  KEY `IX_46B0AE8E` (`sessionId`),
  KEY `IX_E4EFBA8D` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserTracker`
--

LOCK TABLES `UserTracker` WRITE;
/*!40000 ALTER TABLE `UserTracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserTracker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserTrackerPath`
--

DROP TABLE IF EXISTS `UserTrackerPath`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserTrackerPath` (
  `userTrackerPathId` bigint(20) NOT NULL,
  `userTrackerId` bigint(20) DEFAULT NULL,
  `path_` longtext,
  `pathDate` datetime DEFAULT NULL,
  PRIMARY KEY (`userTrackerPathId`),
  KEY `IX_14D8BCC0` (`userTrackerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserTrackerPath`
--

LOCK TABLES `UserTrackerPath` WRITE;
/*!40000 ALTER TABLE `UserTrackerPath` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserTrackerPath` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User_`
--

DROP TABLE IF EXISTS `User_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User_` (
  `uuid_` varchar(75) DEFAULT NULL,
  `userId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `defaultUser` tinyint(4) DEFAULT NULL,
  `contactId` bigint(20) DEFAULT NULL,
  `password_` varchar(75) DEFAULT NULL,
  `passwordEncrypted` tinyint(4) DEFAULT NULL,
  `passwordReset` tinyint(4) DEFAULT NULL,
  `passwordModifiedDate` datetime DEFAULT NULL,
  `digest` varchar(255) DEFAULT NULL,
  `reminderQueryQuestion` varchar(75) DEFAULT NULL,
  `reminderQueryAnswer` varchar(75) DEFAULT NULL,
  `graceLoginCount` int(11) DEFAULT NULL,
  `screenName` varchar(75) DEFAULT NULL,
  `emailAddress` varchar(75) DEFAULT NULL,
  `facebookId` bigint(20) DEFAULT NULL,
  `openId` varchar(1024) DEFAULT NULL,
  `portraitId` bigint(20) DEFAULT NULL,
  `languageId` varchar(75) DEFAULT NULL,
  `timeZoneId` varchar(75) DEFAULT NULL,
  `greeting` varchar(255) DEFAULT NULL,
  `comments` longtext,
  `firstName` varchar(75) DEFAULT NULL,
  `middleName` varchar(75) DEFAULT NULL,
  `lastName` varchar(75) DEFAULT NULL,
  `jobTitle` varchar(100) DEFAULT NULL,
  `loginDate` datetime DEFAULT NULL,
  `loginIP` varchar(75) DEFAULT NULL,
  `lastLoginDate` datetime DEFAULT NULL,
  `lastLoginIP` varchar(75) DEFAULT NULL,
  `lastFailedLoginDate` datetime DEFAULT NULL,
  `failedLoginAttempts` int(11) DEFAULT NULL,
  `lockout` tinyint(4) DEFAULT NULL,
  `lockoutDate` datetime DEFAULT NULL,
  `agreedToTermsOfUse` tinyint(4) DEFAULT NULL,
  `emailAddressVerified` tinyint(4) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `IX_615E9F7A` (`companyId`,`emailAddress`),
  UNIQUE KEY `IX_C5806019` (`companyId`,`screenName`),
  UNIQUE KEY `IX_9782AD88` (`companyId`,`userId`),
  UNIQUE KEY `IX_5ADBE171` (`contactId`),
  KEY `IX_3A1E834E` (`companyId`),
  KEY `IX_6EF03E4E` (`companyId`,`defaultUser`),
  KEY `IX_1D731F03` (`companyId`,`facebookId`),
  KEY `IX_89509087` (`companyId`,`openId`(255)),
  KEY `IX_F6039434` (`companyId`,`status`),
  KEY `IX_762F63C6` (`emailAddress`),
  KEY `IX_A18034A4` (`portraitId`),
  KEY `IX_E0422BDA` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User_`
--

LOCK TABLES `User_` WRITE;
/*!40000 ALTER TABLE `User_` DISABLE KEYS */;
INSERT INTO `User_` VALUES ('ad3c0436-4134-438a-b1da-433cfd87d144',10158,10154,'2013-07-10 10:34:13','2013-07-10 10:34:13',1,10159,'password',0,0,NULL,'5533ed38b5e33c076a804bb4bca644f9,528f53719430814f22dbf509e0faa0c4,528f53719430814f22dbf509e0faa0c4','','',0,'10158','default@liferay.com',0,'',0,'en_US','GMT','Welcome!','','','','','','2013-07-10 10:34:13','',NULL,'',NULL,0,0,NULL,1,0,0),('9d37b9be-4df9-4c58-a6b6-3544b43a96cc',10196,10154,'2013-07-10 10:34:15','2013-07-10 10:34:15',0,10197,'qUqP5cyxm6YcTAhz05Hph5gvu9M=',1,0,NULL,'','','',0,'test','test@liferay.com',0,'',0,'en_US','GMT','Welcome Test Test!','','Test','','Test','','2013-07-10 10:34:15','','2013-07-10 10:34:15','',NULL,0,0,NULL,0,1,0);
/*!40000 ALTER TABLE `User_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users_Groups`
--

DROP TABLE IF EXISTS `Users_Groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users_Groups` (
  `userId` bigint(20) NOT NULL,
  `groupId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`groupId`),
  KEY `IX_C4F9E699` (`groupId`),
  KEY `IX_F10B6C6B` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users_Groups`
--

LOCK TABLES `Users_Groups` WRITE;
/*!40000 ALTER TABLE `Users_Groups` DISABLE KEYS */;
INSERT INTO `Users_Groups` VALUES (10196,10180);
/*!40000 ALTER TABLE `Users_Groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users_Orgs`
--

DROP TABLE IF EXISTS `Users_Orgs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users_Orgs` (
  `userId` bigint(20) NOT NULL,
  `organizationId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`organizationId`),
  KEY `IX_7EF4EC0E` (`organizationId`),
  KEY `IX_FB646CA6` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users_Orgs`
--

LOCK TABLES `Users_Orgs` WRITE;
/*!40000 ALTER TABLE `Users_Orgs` DISABLE KEYS */;
/*!40000 ALTER TABLE `Users_Orgs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users_Permissions`
--

DROP TABLE IF EXISTS `Users_Permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users_Permissions` (
  `userId` bigint(20) NOT NULL,
  `permissionId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`permissionId`),
  KEY `IX_8AE58A91` (`permissionId`),
  KEY `IX_C26AA64D` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users_Permissions`
--

LOCK TABLES `Users_Permissions` WRITE;
/*!40000 ALTER TABLE `Users_Permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `Users_Permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users_Roles`
--

DROP TABLE IF EXISTS `Users_Roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users_Roles` (
  `userId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`),
  KEY `IX_C19E5F31` (`roleId`),
  KEY `IX_C1A01806` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users_Roles`
--

LOCK TABLES `Users_Roles` WRITE;
/*!40000 ALTER TABLE `Users_Roles` DISABLE KEYS */;
INSERT INTO `Users_Roles` VALUES (10196,10161),(10158,10162),(10196,10164),(10196,10165);
/*!40000 ALTER TABLE `Users_Roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users_Teams`
--

DROP TABLE IF EXISTS `Users_Teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users_Teams` (
  `userId` bigint(20) NOT NULL,
  `teamId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`teamId`),
  KEY `IX_4D06AD51` (`teamId`),
  KEY `IX_A098EFBF` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users_Teams`
--

LOCK TABLES `Users_Teams` WRITE;
/*!40000 ALTER TABLE `Users_Teams` DISABLE KEYS */;
/*!40000 ALTER TABLE `Users_Teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users_UserGroups`
--

DROP TABLE IF EXISTS `Users_UserGroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users_UserGroups` (
  `userGroupId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY (`userGroupId`,`userId`),
  KEY `IX_66FF2503` (`userGroupId`),
  KEY `IX_BE8102D6` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users_UserGroups`
--

LOCK TABLES `Users_UserGroups` WRITE;
/*!40000 ALTER TABLE `Users_UserGroups` DISABLE KEYS */;
/*!40000 ALTER TABLE `Users_UserGroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VirtualHost`
--

DROP TABLE IF EXISTS `VirtualHost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VirtualHost` (
  `virtualHostId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `layoutSetId` bigint(20) DEFAULT NULL,
  `hostname` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`virtualHostId`),
  UNIQUE KEY `IX_A083D394` (`companyId`,`layoutSetId`),
  UNIQUE KEY `IX_431A3960` (`hostname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VirtualHost`
--

LOCK TABLES `VirtualHost` WRITE;
/*!40000 ALTER TABLE `VirtualHost` DISABLE KEYS */;
INSERT INTO `VirtualHost` VALUES (10157,10154,0,'localhost');
/*!40000 ALTER TABLE `VirtualHost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WebDAVProps`
--

DROP TABLE IF EXISTS `WebDAVProps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WebDAVProps` (
  `webDavPropsId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `props` longtext,
  PRIMARY KEY (`webDavPropsId`),
  UNIQUE KEY `IX_97DFA146` (`classNameId`,`classPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WebDAVProps`
--

LOCK TABLES `WebDAVProps` WRITE;
/*!40000 ALTER TABLE `WebDAVProps` DISABLE KEYS */;
/*!40000 ALTER TABLE `WebDAVProps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Website`
--

DROP TABLE IF EXISTS `Website`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Website` (
  `websiteId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `url` longtext,
  `typeId` int(11) DEFAULT NULL,
  `primary_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`websiteId`),
  KEY `IX_96F07007` (`companyId`),
  KEY `IX_4F0F0CA7` (`companyId`,`classNameId`),
  KEY `IX_F960131C` (`companyId`,`classNameId`,`classPK`),
  KEY `IX_1AA07A6D` (`companyId`,`classNameId`,`classPK`,`primary_`),
  KEY `IX_F75690BB` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Website`
--

LOCK TABLES `Website` WRITE;
/*!40000 ALTER TABLE `Website` DISABLE KEYS */;
/*!40000 ALTER TABLE `Website` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WikiNode`
--

DROP TABLE IF EXISTS `WikiNode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WikiNode` (
  `uuid_` varchar(75) DEFAULT NULL,
  `nodeId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `lastPostDate` datetime DEFAULT NULL,
  PRIMARY KEY (`nodeId`),
  UNIQUE KEY `IX_920CD8B1` (`groupId`,`name`),
  UNIQUE KEY `IX_7609B2AE` (`uuid_`,`groupId`),
  KEY `IX_5D6FE3F0` (`companyId`),
  KEY `IX_B480A672` (`groupId`),
  KEY `IX_6C112D7C` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WikiNode`
--

LOCK TABLES `WikiNode` WRITE;
/*!40000 ALTER TABLE `WikiNode` DISABLE KEYS */;
/*!40000 ALTER TABLE `WikiNode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WikiPage`
--

DROP TABLE IF EXISTS `WikiPage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WikiPage` (
  `uuid_` varchar(75) DEFAULT NULL,
  `pageId` bigint(20) NOT NULL,
  `resourcePrimKey` bigint(20) DEFAULT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `nodeId` bigint(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `version` double DEFAULT NULL,
  `minorEdit` tinyint(4) DEFAULT NULL,
  `content` longtext,
  `summary` longtext,
  `format` varchar(75) DEFAULT NULL,
  `head` tinyint(4) DEFAULT NULL,
  `parentTitle` varchar(255) DEFAULT NULL,
  `redirectTitle` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`pageId`),
  UNIQUE KEY `IX_3D4AF476` (`nodeId`,`title`,`version`),
  UNIQUE KEY `IX_2CD67C81` (`resourcePrimKey`,`nodeId`,`version`),
  UNIQUE KEY `IX_899D3DFB` (`uuid_`,`groupId`),
  KEY `IX_A2001730` (`format`),
  KEY `IX_C8A9C476` (`nodeId`),
  KEY `IX_E7F635CA` (`nodeId`,`head`),
  KEY `IX_65E84AF4` (`nodeId`,`head`,`parentTitle`),
  KEY `IX_9F7655DA` (`nodeId`,`head`,`parentTitle`,`status`),
  KEY `IX_432F0AB0` (`nodeId`,`head`,`status`),
  KEY `IX_46EEF3C8` (`nodeId`,`parentTitle`),
  KEY `IX_1ECC7656` (`nodeId`,`redirectTitle`),
  KEY `IX_546F2D5C` (`nodeId`,`status`),
  KEY `IX_997EEDD2` (`nodeId`,`title`),
  KEY `IX_E745EA26` (`nodeId`,`title`,`head`),
  KEY `IX_BEA33AB8` (`nodeId`,`title`,`status`),
  KEY `IX_B771D67` (`resourcePrimKey`,`nodeId`),
  KEY `IX_94D1054D` (`resourcePrimKey`,`nodeId`,`status`),
  KEY `IX_FBBE7C96` (`userId`,`nodeId`,`status`),
  KEY `IX_9C0E478F` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WikiPage`
--

LOCK TABLES `WikiPage` WRITE;
/*!40000 ALTER TABLE `WikiPage` DISABLE KEYS */;
/*!40000 ALTER TABLE `WikiPage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WikiPageResource`
--

DROP TABLE IF EXISTS `WikiPageResource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WikiPageResource` (
  `uuid_` varchar(75) DEFAULT NULL,
  `resourcePrimKey` bigint(20) NOT NULL,
  `nodeId` bigint(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`resourcePrimKey`),
  UNIQUE KEY `IX_21277664` (`nodeId`,`title`),
  KEY `IX_BE898221` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WikiPageResource`
--

LOCK TABLES `WikiPageResource` WRITE;
/*!40000 ALTER TABLE `WikiPageResource` DISABLE KEYS */;
/*!40000 ALTER TABLE `WikiPageResource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WorkflowDefinitionLink`
--

DROP TABLE IF EXISTS `WorkflowDefinitionLink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WorkflowDefinitionLink` (
  `workflowDefinitionLinkId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `typePK` bigint(20) DEFAULT NULL,
  `workflowDefinitionName` varchar(75) DEFAULT NULL,
  `workflowDefinitionVersion` int(11) DEFAULT NULL,
  PRIMARY KEY (`workflowDefinitionLinkId`),
  KEY `IX_A8B0D276` (`companyId`),
  KEY `IX_A4DB1F0F` (`companyId`,`workflowDefinitionName`,`workflowDefinitionVersion`),
  KEY `IX_B6EE8C9E` (`groupId`,`companyId`,`classNameId`),
  KEY `IX_1E5B9905` (`groupId`,`companyId`,`classNameId`,`classPK`),
  KEY `IX_705B40EE` (`groupId`,`companyId`,`classNameId`,`classPK`,`typePK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WorkflowDefinitionLink`
--

LOCK TABLES `WorkflowDefinitionLink` WRITE;
/*!40000 ALTER TABLE `WorkflowDefinitionLink` DISABLE KEYS */;
/*!40000 ALTER TABLE `WorkflowDefinitionLink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WorkflowInstanceLink`
--

DROP TABLE IF EXISTS `WorkflowInstanceLink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WorkflowInstanceLink` (
  `workflowInstanceLinkId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `workflowInstanceId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`workflowInstanceLinkId`),
  KEY `IX_415A7007` (`groupId`,`companyId`,`classNameId`,`classPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WorkflowInstanceLink`
--

LOCK TABLES `WorkflowInstanceLink` WRITE;
/*!40000 ALTER TABLE `WorkflowInstanceLink` DISABLE KEYS */;
/*!40000 ALTER TABLE `WorkflowInstanceLink` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-07-10 12:36:33
