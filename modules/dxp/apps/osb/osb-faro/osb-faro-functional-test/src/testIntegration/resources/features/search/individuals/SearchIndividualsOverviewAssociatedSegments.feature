@spira_Search @Segments @Search @team_FARO @priority_3
Feature: Search Associated Segment from Individuals Overview
	As a Business User, I should be able to search for associated segment for individuals overview
  
	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page
	  
	Scenario: Search Associated Segment
		Given I click the "Known Individuals" tab
		And I click "Abram Bauch" in the table
		When I search for "everybody" in the Associated Segments card
		Then I should see "everybody" in the card list "Associated Segments"