@spira_Segments @component_Segments @team_FARO @priority_3
Feature: Segment interest details has a tab that shows inactive pages
	As a Business User, I should be able to see a tab with inactive pages in segment

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
	  
	Scenario: Inactive pages shown
		Given I click "everybody" in the table
		And I click the "Interests" tab
		And I click "rich e-commerce" in the table
		When I click the "Inactive Pages" tab
		Then I should see text saying "There are no Pages found." on the page