@spira_Segments @component_Segments @team_FARO @priority_3
Feature: Segment interest details has a tab that shows active pages
	As a Business User, I should be able to see a tab with active pages in segment

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: Active pages shown
		Given I click "everybody" in the table
		And I click the "Interests" tab
		And I click "rich e-commerce" in the table
		When I click the "Active Pages" tab
		Then I should see page named "orchestrate rich e-commerce" in the table